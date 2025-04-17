package com.boots.message_store_poc.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.boots.message_store_poc.dto.Message;
import com.boots.message_store_poc.dto.MessageSummary;
import com.boots.message_store_poc.dto.PaginatedMessageSummary;
import com.boots.message_store_poc.model.MessageSummaryProjection;
import com.boots.message_store_poc.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;

	@Override
	public PaginatedMessageSummary getMessageSummaries(Pageable pageable) {
		
		Page<MessageSummaryProjection> page = messageRepository.findAllSummariesBy(pageable);
		List<MessageSummary> summaries = page.stream().map(message -> new MessageSummary(
				message.getId(),
				message.getSourceSystem(),
				message.getDestinationAddress(),
				message.getMessageId(),
				message.getCorrelationId(),
				message.getMessageRenderTechnology())).collect(Collectors.toList());

		return new PaginatedMessageSummary(summaries, page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
	}
	
	@Override
	public Message getMessage(String id) {
		return messageRepository.findById(id).map(message -> new Message(
				message.getId(),
				message.getSourceSystem(),
				message.getDestinationAddress(),
				message.getMessageId(),
				message.getCorrelationId(),
				message.getMessageRenderTechnology(),
				message.getPayload())).orElse(null);
	}
	
	@Override
	public PaginatedMessageSummary searchMessages(String query, boolean includePayload, Map<String, String> columnFilters, Pageable pageable) {
		// see note in CustomMessageRepositoryImpl about why we use the concrete class instead of the projection interface
		Page<MessageSummary> page = messageRepository.search(query, includePayload, columnFilters, pageable);
		List<MessageSummary> summaries = page.stream().map(message -> new MessageSummary(
				message.getId(),
				message.getSourceSystem(),
				message.getDestinationAddress(),
				message.getMessageId(),
				message.getCorrelationId(),
				message.getMessageRenderTechnology())).collect(Collectors.toList());

		return new PaginatedMessageSummary(
				summaries,
				page.getTotalElements(),
				page.getTotalPages(),
				page.getNumber(),
				page.getSize());
	}
}
