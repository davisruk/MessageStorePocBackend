package com.boots.message_store_poc.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boots.message_store_poc.dto.MessageSummary;
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
}
