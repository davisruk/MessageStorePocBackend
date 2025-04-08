package com.boots.message_store_poc.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.boots.message_store_poc.dto.MessageSummary;

public interface CustomMessageRepository {
	Page<MessageSummary> search(String query, boolean includePayload, Map<String, String> columnFilters, Pageable pageable);
}
