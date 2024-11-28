package org.example;

import org.example.DAOrealisation.TicketDAO;
import org.example.DAOrealisation.UserDAO;
import org.example.models.Ticket;
import org.example.models.TicketType;
import org.example.models.User;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        TicketDAO ticketDAO = new TicketDAO();

        try {
            // Clear database (optional, for testing purposes)
            userDAO.deleteAll();

            // Step 1: Save Users
            User user1 = new User("Alice");
            userDAO.save(user1);

            User user2 = new User("Bob");
            userDAO.save(user2);

            // Step 2: Fetch All Users
            List<User> users = userDAO.getAll();
            users.forEach(user -> System.out.println("User ID: " + user.getId() + ", Name: " + user.getName()));

            // Step 3: Update a User's Name
            User userToUpdate = userDAO.getById(user1.getId()); // Use updated getById with JOIN FETCH
            userToUpdate.setName("Alice Updated");
            userDAO.update(userToUpdate); // Persist the updated user

            // Step 4: Save Tickets for the Updated User
            Ticket ticket1 = new Ticket(userToUpdate, TicketType.DAY);
            Ticket ticket2 = new Ticket(userToUpdate, TicketType.MONTH);

            ticketDAO.save(ticket1);
            ticketDAO.save(ticket2);

            // Step 5: Fetch All Tickets
            List<Ticket> tickets = ticketDAO.getAll();
            tickets.forEach(ticket -> System.out.println("Ticket ID: " + ticket.getId() + ", Type: " + ticket.getTicketType()));

            // Step 6: Delete User and Their Tickets
            List<Ticket> userTickets = ticketDAO.getAllByUserId(userToUpdate.getId());
            userTickets.forEach(ticket -> ticketDAO.delete(ticket.getId())); // Delete tickets associated with the user

            userDAO.delete(userToUpdate.getId()); // Delete the user

            System.out.println("Remaining Users: " + userDAO.getAll());
            System.out.println("Remaining Tickets: " + ticketDAO.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}