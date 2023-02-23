package com.cameriere.ticket.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cameriere.ticket.models.Ticket;

@Repository
public interface TicketDAO extends JpaRepository<Ticket, Integer> {

}
