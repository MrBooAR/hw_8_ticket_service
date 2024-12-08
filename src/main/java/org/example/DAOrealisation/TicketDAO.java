package org.example.DAOrealisation;

import org.example.models.Ticket;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TicketDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TicketDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Ticket ticket) {
        sessionFactory.getCurrentSession().save(ticket);
    }

    @Transactional
    public Ticket getById(int id) {
        return sessionFactory.getCurrentSession().get(Ticket.class, id);
    }

    @Transactional
    public List<Ticket> getAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM Ticket", Ticket.class).list();
    }

    @Transactional
    public void delete(int id) {
        Ticket ticket = sessionFactory.getCurrentSession().get(Ticket.class, id);
        if (ticket != null) {
            sessionFactory.getCurrentSession().delete(ticket);
        }
    }

    @Transactional
    public List<Ticket> getTicketsByUserId(int userId) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Ticket WHERE user.id = :userId", Ticket.class)
                .setParameter("userId", userId)
                .list();
    }
}