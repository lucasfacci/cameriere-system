package com.cameriere.ticket.consumers;

import com.cameriere.ticket.models.Ticket;
import com.cameriere.ticket.services.TicketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketConsumer {

	TicketService ticketService;

	public TicketConsumer(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@RabbitListener(queues="${spring.rabbitmq.queue}")
	public void consume(@Payload String payload) {
		String[] info = payload.split(";");
		Ticket ticket = new Ticket();
		String products = info[3].replaceAll("[\\[\\]]", "");
		ticket.setProducts(List.of(products.split(", ")));
		ticketService.save(ticket);
	}
}
