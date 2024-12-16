package org.example.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}