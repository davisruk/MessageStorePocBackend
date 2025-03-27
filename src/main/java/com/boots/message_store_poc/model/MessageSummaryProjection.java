package com.boots.message_store_poc.model;

public interface MessageSummaryProjection {
	String getId();
	String getSourceSystem();
	String getDestinationAddress();
	String getMessageId();
	String getCorrelationId();
	String getMessageRenderTechnology();
}
