package com.cameriere.order.producers;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
	private RabbitTemplate rabbitTemplate;
	private Queue queue;

	public OrderProducer(RabbitTemplate rabbitTemplate, Queue queue) {
		this.rabbitTemplate = rabbitTemplate;
		this.queue = queue;
	}

	public void send(String message) {
		rabbitTemplate.convertAndSend(this.queue.getName(), message);
	}
}