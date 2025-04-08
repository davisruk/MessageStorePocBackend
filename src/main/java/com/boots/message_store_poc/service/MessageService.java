package com.boots.message_store_poc.service;

import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.boots.message_store_poc.dto.Message;
import com.boots.message_store_poc.dto.PaginatedMessageSummary;


public interface MessageService {

	PaginatedMessageSummary getMessageSummaries(Pageable pageable);
	Message getMessage(String id);
	PaginatedMessageSummary searchMessages(String query, boolean includePayload, Map<String, String> columnFilters, Pageable pageable);
}
