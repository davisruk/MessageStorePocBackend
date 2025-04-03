package com.boots.message_store_poc.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.boots.message_store_poc.dto.MessageSummary;
import com.boots.message_store_poc.model.MessageDocument;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class CustomMessageRepositoryImpl implements CustomMessageRepository {

	private final MongoTemplate mongoTemplate;
	
	// Use custom repository method to search for messages
	// This method uses MongoTemplate to search for messages
	// MongoTemplate does not support projections so 
	// we need to use the concrete class instead of the projection interface
	// we could make MessageSummary implement the projection interface
	// but this might be confusing since Spring Data will still create a proxy in the repository
	@Override
	public Page<MessageSummary> search(String query, boolean includePayload, Pageable pageable) {
		List<Criteria> criteria = new ArrayList<>();
		criteria.add(Criteria.where("messageId").regex(query,"i"));
		criteria.add(Criteria.where("sourceSystem").regex(query,"i"));
		criteria.add(Criteria.where("destinationAddress").regex(query,"i"));
		criteria.add(Criteria.where("correlationId").regex(query,"i"));
		criteria.add(Criteria.where("messageRenderTechnology").regex(query,"i"));
		
		if (includePayload) {
			criteria.add(Criteria.where("payload").regex(query,"i"));
		}
		
		Criteria combined = new Criteria().orOperator(criteria.toArray(new Criteria[0]));
		Query mongoQuery = new Query(combined).with(pageable);
		List<MessageSummary> messages = mongoTemplate.find(mongoQuery, MessageDocument.class).stream()
				.map(message -> new MessageSummary(
						message.getId(),
						message.getSourceSystem(),
						message.getDestinationAddress(),
						message.getMessageId(),
						message.getCorrelationId(),
						message.getMessageRenderTechnology())).toList();
		long count = mongoTemplate.count(Query.of(mongoQuery).limit(-1).skip(-1), MessageDocument.class);
		return new PageImpl<MessageSummary>(messages, pageable, count);
	}
}
