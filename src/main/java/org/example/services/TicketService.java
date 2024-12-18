package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.interfaces.TicketRepository;
import org.example.models.Ticket;
import org.example.models.User;
import org.example.models.enums.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    @Autowired
    public TicketService(TicketRepository ticketRepository, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.ticketRepository = ticketRepository;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket createTicket(User user, TicketType type, String ticketClass, LocalDate startDate, BigDecimal price) {
        Ticket ticket = new Ticket(user, type, ticketClass, startDate, price);
        return ticketRepository.save(ticket);
    }

    public void deleteTicket(long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    public Ticket getTicketById(long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + ticketId));
    }

    public List<Ticket> loadTicketsFromFile() {
        try {
            Resource resource = resourceLoader.getResource("classpath:ticket_data.json");
            List<Ticket> tickets = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<Ticket>>() {}
            );

            return ticketRepository.saveAll(tickets);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load tickets from JSON file", e);
        }
    }
}