package com.cameriere.ticket.consumers;

import java.time.LocalDateTime;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.cameriere.ticket.repositories.TicketRepository;
import com.cameriere.ticket.models.Ticket;

@Service
public class TicketConsumer {

	@Autowired
	TicketRepository dao;

	@Value("${topic.name.consumer}")
	private String topicName;
	
	@KafkaListener(topics = "${topic.name.consumer}", groupId = "group_id")
	public void consume(ConsumerRecord<String, String> payload) {
		String[] info = payload.value().split(";");
		Ticket ticket = new Ticket();
		ticket.setCreatedAt(LocalDateTime.parse(info[2]));
		System.out.println(ticket);
	}
}
