package com.boots.message_store_poc.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.boots.message_store_poc.service.MessageListener;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfig {

	@Bean
	public Queue messageQueue() {
		Queue q = new Queue("message.queue", true);
		return q;
	}
	
    @Bean
    public Jackson2JsonMessageConverter converter() {
    	// some of our messages include control characters
    	// Jackson does not allow these by default
    	// Use the builder to enable this feature
        JsonFactory jsonFactory = JsonFactory.builder()
        		.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
        		.build();
    	ObjectMapper objectMapper = new ObjectMapper(jsonFactory);
    	return new Jackson2JsonMessageConverter(objectMapper);
    }
    
    @Bean // use @Qualifier to help	Spring order the bean dependencies correctly
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, @Qualifier("converter") Jackson2JsonMessageConverter converter) {
    	RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(converter);
		return template;
	}
    
    @Bean // use @Qualifier to help	Spring order the bean dependencies correctly
    public MessageListenerAdapter listenerAdapter(@Qualifier("converter") Jackson2JsonMessageConverter converter, MessageListener messageListener){
    	MessageListenerAdapter adapter = new MessageListenerAdapter(messageListener, "receiveMessage");
		adapter.setMessageConverter(converter);
		return adapter;
	}

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
    	SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setMessageListener(listenerAdapter);
		return container;
	}
}
