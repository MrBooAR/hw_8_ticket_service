package org.example;

import org.example.DAOrealisation.TicketDAO;
import org.example.DAOrealisation.UserDAO;
import org.example.database.HibernateConfig;
import org.example.database.SpringConfig;
import org.example.models.Ticket;
import org.example.models.TicketType;
import org.example.models.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class, HibernateConfig.class)) {
            UserDAO userDAO = context.getBean(UserDAO.class);
            TicketDAO ticketDAO = context.getBean(TicketDAO.class);

            // Save user
            User user = new User("Alice");
            userDAO.save(user);

            // Update user
            user.setName("Alice Updated");
            userDAO.update(user);

            // Save tickets for user
            Ticket ticket1 = new Ticket(user, TicketType.DAY);
            Ticket ticket2 = new Ticket(user, TicketType.MONTH);
            ticketDAO.save(ticket1);
            ticketDAO.save(ticket2);

            // Fetch and print tickets
            ticketDAO.getAll().forEach(ticket -> System.out.println("Ticket: " + ticket.getTicketType()));

            // Delete user and their tickets
            ticketDAO.getTicketsByUserId(user.getId()).forEach(ticket -> ticketDAO.delete(ticket.getId()));
            userDAO.delete(user.getId());
        }
    }
}