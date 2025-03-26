package com.boots.message_store_poc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.boots.message_store_poc.dto.PaginatedMessageSummary;


public interface MessageService {

	PaginatedMessageSummary getMessageSummaries(Pageable pageable);
}
