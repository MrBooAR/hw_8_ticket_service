package org.example.services;

import org.example.interfaces.UserRepository;
import org.example.models.Ticket;
import org.example.models.User;
import org.example.models.enums.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Value("${feature.update-user-and-create-ticket.enabled}")
    private boolean isUpdateUserAndCreateTicketEnabled;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(String name) {
        User user = new User(name);
        return userRepository.save(user);
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    public User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Transactional
    public void activateUserAndCreateTicket(int userId, TicketType ticketType) {
        if (!isUpdateUserAndCreateTicketEnabled) {
            throw new RuntimeException("The feature to update user and create ticket is disabled!");
        }

        User user = getUserById(userId);
        user.setStatus(org.example.models.enums.Status.ACTIVE);

        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setCreationDate(LocalDateTime.now());

        user.addTicket(ticket); // Bidirectional mapping
        userRepository.save(user);
    }
}