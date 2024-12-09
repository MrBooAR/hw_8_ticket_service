package org.example.DAOrealisation;

import org.example.models.User;
import org.example.models.Ticket;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Transactional
    public User getById(int id) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT u FROM User u LEFT JOIN FETCH u.tickets WHERE u.id = :id", User.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Transactional
    public List<User> getAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM User", User.class).list();
    }

    @Transactional
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Transactional
    public void delete(int id) {
        User user = sessionFactory.getCurrentSession().get(User.class, id);
        if (user != null) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }

    @Transactional
    public void deleteAll() {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM User").executeUpdate();
    }

    @Transactional
    public void activateUserAndCreateTicket(int userId, Ticket ticket) {
        // Fetch the user entity
        User user = sessionFactory.getCurrentSession().get(User.class, userId);
        if (user != null) {
            user.setStatus("ACTIVATED");
            sessionFactory.getCurrentSession().update(user);
            ticket.setUser(user);
            sessionFactory.getCurrentSession().save(ticket);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }
}