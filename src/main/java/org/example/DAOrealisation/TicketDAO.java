package org.example.DAOrealisation;

import org.example.interfaces.DAO;
import org.example.models.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class TicketDAO implements DAO<Ticket> {

    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public void save(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(ticket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Ticket getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Ticket.class, id);
        }
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Ticket WHERE user.id = :userId", Ticket.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

    @Override
    public List<Ticket> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Ticket", Ticket.class).list(); // Use the entity name
        }
    }

    @Override
    public void update(Ticket ticket) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(ticket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            if (ticket != null) {
                session.delete(ticket);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Ticket> getAllByUserId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Ticket t WHERE t.user.id = :userId", Ticket.class)
                    .setParameter("userId", userId)
                    .list();
        }
    }

}