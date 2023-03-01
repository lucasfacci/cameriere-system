package com.cameriere.ticket.services;

import com.cameriere.ticket.repositories.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
}
