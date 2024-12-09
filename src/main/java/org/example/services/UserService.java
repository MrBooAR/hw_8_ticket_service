package org.example.services;

import org.example.DAOrealisation.UserDAO;
import org.example.models.Ticket;
import org.example.models.TicketType;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserDAO userDAO;

    @Value("${feature.update-user-and-create-ticket.enabled}")
    private boolean isUpdateUserAndCreateTicketEnabled;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public User createUser(String name) {
        User user = new User(name);
        userDAO.save(user);
        return user;
    }

    public void deleteUser(int userId) {
        userDAO.delete(userId);
    }

    public User getUserById(int userId) {
        return userDAO.getById(userId);
    }

    @Transactional
    public void activateUserAndCreateTicket(int userId, TicketType ticketType) {
        if (!isUpdateUserAndCreateTicketEnabled) {
            throw new RuntimeException("The feature to update user and create ticket is disabled!");
        }
        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setCreationDate(LocalDateTime.now());
        userDAO.activateUserAndCreateTicket(userId, ticket);
    }
}
