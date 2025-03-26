package com.boots.message_store_poc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.boots.message_store_poc.model.MessageDocument;

@Repository
public interface MessageRepository extends MongoRepository<MessageDocument, String> {
	//Page<MessageDocument> getMessageDocuments(Pageable pageable);
}
