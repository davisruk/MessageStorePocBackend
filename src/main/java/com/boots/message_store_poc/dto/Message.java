package com.boots.message_store_poc.dto;

public class Message extends MessageSummary {
	private String payload;
	private String formatUrl;

	public Message(String id, String sourceSystem, String destinationAddress, String messageId, String correlationId,
			String messageRenderTechnology, String payload, String formatUrl, String formattedPayload) {
		super(id, sourceSystem, destinationAddress, messageId, correlationId, messageRenderTechnology);
		this.payload = payload;
		this.formatUrl = formatUrl;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public String getFormatUrl() {
		return formatUrl;
	}
	public void setFormatUrl(String formatUrl) {
		this.formatUrl = formatUrl;
	}
}
