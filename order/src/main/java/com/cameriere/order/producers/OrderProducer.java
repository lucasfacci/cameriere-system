package com.cameriere.order.producers;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

	@Value("${spring.rabbitmq.queue}")
	private Queue queue;
	private RabbitTemplate rabbitTemplate;

	public OrderProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void send(String message) {
		rabbitTemplate.convertAndSend(queue.getName(), message);
	}
}