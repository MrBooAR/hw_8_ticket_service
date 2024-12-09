package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.DAOrealisation.TicketDAO;
import org.example.models.Ticket;
import org.example.models.TicketType;
import org.example.models.User;
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

    private final TicketDAO ticketDAO;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    @Autowired
    public TicketService(TicketDAO ticketDAO, ResourceLoader resourceLoader, ObjectMapper objectMapper) {
        this.ticketDAO = ticketDAO;
        this.resourceLoader = resourceLoader;
        this.objectMapper = objectMapper;
    }

    public List<Ticket> getAllTickets() {
        return ticketDAO.getAll();
    }

    public Ticket createTicket(User user, TicketType type, String ticketClass, LocalDate startDate, BigDecimal price) {
        Ticket ticket = new Ticket(user, type, ticketClass, startDate, price);
        ticketDAO.save(ticket);
        return ticket;
    }

    public void deleteTicket(int ticketId) {
        ticketDAO.delete(ticketId);
    }

    public Ticket getTicketById(int ticketId) {
        return ticketDAO.getById(ticketId);
    }

    public List<Ticket> loadTicketsFromFile() {
        try {
            Resource resource = resourceLoader.getResource("classpath:ticket_data.json");
            List<Ticket> tickets = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<Ticket>>() {}
            );

            return tickets;

        } catch (IOException e) {
            throw new RuntimeException("Failed to load tickets from JSON file", e);
        }
    }
}