package com.cameriere.ticket.services;

import com.cameriere.ticket.models.Ticket;
import com.cameriere.ticket.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Ticket save(Ticket ticketModel) {
        return ticketRepository.save(ticketModel);
    }
}
