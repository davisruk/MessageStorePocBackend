package com.boots.message_store_poc.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "messages")
public class MessageDocument {
	@Id
	private String id;
	private String sourceSystem;
	private String destinationAddress;
	private String messageId;
	private String correlationId;
	private String messageRenderTechnology;
	private String payload;
	private String formatUrl;
}
