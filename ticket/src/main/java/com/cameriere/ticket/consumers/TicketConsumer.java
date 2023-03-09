package com.cameriere.ticket.consumers;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class TicketConsumer {

	@RabbitListener(queues="${spring.rabbitmq.queue}")
	public void consume(@Payload String message) {
//		String[] info = payload.value().split(";");
		System.out.println(message);
	}
}
