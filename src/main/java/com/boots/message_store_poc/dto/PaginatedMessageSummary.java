package com.boots.message_store_poc.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PaginatedMessageSummary {
	private List<MessageSummary> content;
	private long totalElements;
	private int totalPages;
	private int pageNumber;
}
