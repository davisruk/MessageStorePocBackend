package com.boots.message_store_poc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Message extends MessageSummary {
	private String payload;

	public Message(String id, String sourceSystem, String destinationAddress, String messageId, String correlationId,
			String messageRenderTechnology, String payload) {
		super(id, sourceSystem, destinationAddress, messageId, correlationId, messageRenderTechnology);
		this.payload = payload;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}	
}
