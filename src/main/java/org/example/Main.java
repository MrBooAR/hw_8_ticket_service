package org.example;

import org.example.database.HibernateConfig;
import org.example.database.SpringConfig;
import org.example.models.Ticket;
import org.example.models.TicketType;
import org.example.models.User;
import org.example.services.TicketService;
import org.example.services.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class, HibernateConfig.class)) {
            UserService userService = context.getBean(UserService.class);
            TicketService ticketService = context.getBean(TicketService.class);

            // 1. Create and save a new user
            User user = userService.createUser("Alice");
            System.out.println("Created User: " + user.getName());

            // 2. Create a ticket for the user with the updated method
            Ticket createdTicket = ticketService.createTicket(
                    user,
                    TicketType.DAY,
                    "STD",
                    LocalDate.of(2025, 1, 1),
                    BigDecimal.valueOf(50)
            );
            System.out.println("Created Ticket: " + createdTicket.getTicketType() + ", Class: " + createdTicket.getTicketClass());

            // 3. Load tickets from JSON file
            System.out.println("Loading tickets from JSON file...");
            List<Ticket> ticketsFromFile = ticketService.loadTicketsFromFile();
            ticketsFromFile.forEach(ticket -> System.out.println(
                    "Loaded Ticket - Type: " + ticket.getTicketType() +
                            ", Class: " + ticket.getTicketClass() +
                            ", Price: " + ticket.getPrice()
            ));

            // 4. Fetch all tickets from the database and print
            List<Ticket> allTickets = ticketService.getAllTickets();
            System.out.println("All Tickets in Database:");
            allTickets.forEach(ticket -> System.out.println(
                    "Ticket Type: " + ticket.getTicketType() +
                            ", Class: " + ticket.getTicketClass() +
                            ", Price: " + ticket.getPrice()
            ));

            // 5. Update user and create a new ticket (testing transactional support and feature toggle)
            try {
                userService.activateUserAndCreateTicket(user.getId(), TicketType.MONTH);
                System.out.println("User activated and new ticket created.");
            } catch (RuntimeException e) {
                System.err.println("Error during transactional operation: " + e.getMessage());
            }

            // 6. Delete the user and their associated tickets
            System.out.println("Deleting User and Associated Tickets...");
            userService.deleteUser(user.getId());
            System.out.println("User and associated tickets deleted.");
        }
    }
}