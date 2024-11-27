package org.example;

import org.example.DAOrealisation.TicketDAO;
import org.example.DAOrealisation.UserDAO;
import org.example.models.Ticket;
import org.example.models.User;
import org.example.managers.TransactionManager;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        TicketDAO ticketDAO = new TicketDAO();

        try {
            // Step 1: Save Users
            System.out.println("Saving users...");
            User user1 = new User("Alice");
            userDAO.save(user1);

            User user2 = new User("Bob");
            userDAO.save(user2);

            // Step 2: Fetch All Users
            System.out.println("\nFetching all users:");
            List<User> users = userDAO.getAll();
            users.forEach(user -> System.out.println("User ID: " + user.getId() + ", Name: " + user.getName()));

            // Step 3: Update a User
            System.out.println("\nUpdating a user...");
            User userToUpdate = users.get(0);
            userToUpdate.setName("Alice Updated");
            userDAO.update(userToUpdate);

            System.out.println("Updated user:");
            User updatedUser = userDAO.getById(userToUpdate.getId());
            System.out.println("User ID: " + updatedUser.getId() + ", Name: " + updatedUser.getName());

            // Step 4: Save Tickets
            System.out.println("\nSaving tickets...");
            Ticket ticket1 = new Ticket(userToUpdate.getId(), "DAY");
            ticketDAO.save(ticket1);

            Ticket ticket2 = new Ticket(userToUpdate.getId(), "MONTH");
            ticketDAO.save(ticket2);

            // Step 5: Fetch All Tickets
            System.out.println("\nFetching all tickets:");
            List<Ticket> tickets = ticketDAO.getAll();
            tickets.forEach(ticket -> System.out.println("Ticket ID: " + ticket.getId() + ", User ID: " + ticket.getUserId()
                    + ", Type: " + ticket.getTicketType()));

            // Step 6: Update a Ticket
            System.out.println("\nUpdating a ticket...");
            Ticket ticketToUpdate = tickets.get(0);
            ticketToUpdate.setTicketType("YEAR");
            ticketDAO.update(ticketToUpdate);

            System.out.println("Updated ticket:");
            Ticket updatedTicket = ticketDAO.getById(ticketToUpdate.getId());
            System.out.println("Ticket ID: " + updatedTicket.getId() + ", Type: " + updatedTicket.getTicketType());

            // Step 7: Test Transaction Support with Savepoints
            System.out.println("\nTesting transaction with savepoints...");
            TransactionManager.updateUserAndTicket(
                    userToUpdate.getId(), "Transactional Update", // Update User
                    updatedTicket.getId(), "WEEK"                 // Update Ticket
            );

            // Step 8: Fetch All Data After Transaction
            System.out.println("\nFetching all data after transaction:");
            List<User> finalUsers = userDAO.getAll();
            finalUsers.forEach(user -> System.out.println("User ID: " + user.getId() + ", Name: " + user.getName()));

            List<Ticket> finalTickets = ticketDAO.getAll();
            finalTickets.forEach(ticket -> System.out.println("Ticket ID: " + ticket.getId() + ", User ID: " + ticket.getUserId()
                    + ", Type: " + ticket.getTicketType()));

            // Step 9: Delete User and Their Tickets
            System.out.println("\nDeleting user and their tickets...");
            userDAO.delete(userToUpdate.getId());

            System.out.println("Final users after deletion:");
            List<User> remainingUsers = userDAO.getAll();
            remainingUsers.forEach(user -> System.out.println("User ID: " + user.getId() + ", Name: " + user.getName()));

            System.out.println("Final tickets after deletion:");
            List<Ticket> remainingTickets = ticketDAO.getAll();
            remainingTickets.forEach(ticket -> System.out.println("Ticket ID: " + ticket.getId() + ", Type: " + ticket.getTicketType()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}