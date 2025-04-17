package com.boots.message_store_poc.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boots.message_store_poc.dto.Message;
import com.boots.message_store_poc.dto.PaginatedMessageSummary;
import com.boots.message_store_poc.service.MessageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {
	private final MessageService messageService;
	
	@GetMapping("/summaries")
	public PaginatedMessageSummary getMessageSummaries(Pageable pageable) {
		PaginatedMessageSummary summaries = messageService.getMessageSummaries(pageable);
		return summaries;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Message> getMessage(@PathVariable("id") String id) {
		Message message = messageService.getMessage(id);
		if (message == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(message);
	}
	
	@GetMapping("/search")
	public ResponseEntity<PaginatedMessageSummary> searchMessages(@RequestParam Map<String, String> params) {
		String query = params.remove("query");
		boolean includePayload = Boolean.parseBoolean(params.remove("includePayload"));
		int page = Integer.parseInt(params.remove("page"));
		int size = Integer.parseInt(params.remove("size"));
		return ResponseEntity.ok(messageService.searchMessages(query, includePayload, params, PageRequest.of(page, size)));
	}
	
	@GetMapping("/loadMessageSummaries")
	public ResponseEntity<PaginatedMessageSummary> loadMessageSummaries(
			@RequestParam (name="query") String query,
			@RequestParam (name="includePayload") boolean includePayload,
			@RequestParam (name="page") int page,
			@RequestParam (name="size") int size,
			@RequestParam (required = false, name="sort") List<String> sort,
			@RequestParam MultiValueMap <String, String> allParams) {
		StringBuffer debugStr = new StringBuffer("loadMessageSummaries called with allParams of size: " + allParams.size());
		allParams.forEach((key, value) -> debugStr.append("\n" + key + ": " + value));
		System.out.println(debugStr);
		allParams.remove("query");
		allParams.remove("includePayload");
		allParams.remove("page");
		allParams.remove("size");
		allParams.remove("sort");
		
	    List<Sort.Order> orders = Optional.ofNullable(sort)
	    	      .orElse(List.of())
	    	      .stream()
	    	      .map(raw -> {
	    	        // split on comma, trimming any surrounding whitespace
	    	        String[] parts = raw.split("\\s*,\\s*", 2);
	    	        String property = parts[0];
	    	        // default to ASC if no direction provided
	    	        Sort.Direction dir = (parts.length > 1 && !parts[1].isBlank())
	    	            ? Sort.Direction.fromString(parts[1])
	    	            : Sort.Direction.ASC;
	    	        return new Sort.Order(dir, property);
	    	      })
	    	      .toList();
	    
		Map<String, String> columnFilters = allParams.toSingleValueMap();
		Pageable pageable = PageRequest.of(page, size, orders.isEmpty() ? Sort.unsorted() : Sort.by(orders));
		return ResponseEntity.ok(messageService.searchMessages(query, includePayload, columnFilters, pageable));
	}
}
