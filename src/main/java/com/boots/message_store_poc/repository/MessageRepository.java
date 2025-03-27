package com.boots.message_store_poc.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.boots.message_store_poc.model.MessageDocument;
import com.boots.message_store_poc.model.MessageSummaryProjection;

@Repository
public interface MessageRepository extends MongoRepository<MessageDocument, String> {
	// Use projection to return only the fields we need
	Page<MessageSummaryProjection> findAllSummariesBy(Pageable pageable);
	Optional<MessageDocument> findById(String id);
}
