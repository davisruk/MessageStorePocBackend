package com.boots.message_store_poc.service;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.boots.message_store_poc.model.MessageDocument;
import com.boots.message_store_poc.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageListener {

	private final MessageRepository messageRepository;
	
	@RabbitListener(queues = "message.queue")
	public void receiveMessage(Map<String,Object> messageData) {
		MessageDocument message = new MessageDocument();
		message.setSourceSystem((String) messageData.get("sourceSystem"));
		message.setDestinationAddress((String) messageData.get("destinationAddress"));
		message.setMessageId((String) messageData.get("messageId"));
		message.setCorrelationId((String) messageData.get("correlationId"));
		message.setMessageRenderTechnology((String) messageData.get("messageRenderTechnology"));
		Object payload = messageData.get("payload");
		message.setPayload(payload != null ? payload.toString() : null);
		messageRepository.save(message);
		System.out.println("Message saved to MongoDB: " + message);
	}
}
