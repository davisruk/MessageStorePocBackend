package com.boots.message_store_poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageSummary {
	private String id;
	private String sourceSystem;
	private String destinationAddress;
	private String messageId;
	private String correlationId;
	private String messageRenderTechnology;
}
