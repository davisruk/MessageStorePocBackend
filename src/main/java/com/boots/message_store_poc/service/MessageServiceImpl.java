package com.boots.message_store_poc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.boots.message_store_poc.dto.MessageSummary;
import com.boots.message_store_poc.dto.PaginatedMessageSummary;
import com.boots.message_store_poc.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	private final MessageRepository messageRepository;

	@Override
	public PaginatedMessageSummary getMessageSummaries(Pageable pageable) {
		
		Page<MessageSummary> summaries = messageRepository.findAll(pageable).map(message -> new MessageSummary(
				message.getId(),
				message.getSourceSystem(),
				message.getDestinationAddress(),
				message.getMessageId(),
				message.getCorrelationId(),
				message.getMessageRenderTechnology()));

		return new PaginatedMessageSummary(summaries.getContent(), summaries.getTotalElements(), summaries.getTotalPages(), summaries.getNumber(), summaries.getSize());
	}
}
