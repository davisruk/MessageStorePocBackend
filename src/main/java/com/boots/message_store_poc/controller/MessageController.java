package com.boots.message_store_poc.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<PaginatedMessageSummary> searchMessages(
			@RequestParam(name = "query") String query, 
			@RequestParam(name = "includePayload", defaultValue = "false") boolean includePayload,
			@RequestParam (name = "page", defaultValue = "0") int page,
			@RequestParam (name = "size", defaultValue = "10") int size) {
		PaginatedMessageSummary summaries = messageService.searchMessages(query, includePayload, PageRequest.of(page, size));
		return ResponseEntity.ok(summaries);
	}
}
