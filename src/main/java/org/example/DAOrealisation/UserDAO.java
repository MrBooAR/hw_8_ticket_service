package org.example.DAOrealisation;

import org.example.interfaces.DAO;
import org.example.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class UserDAO implements DAO<User> {
    private final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public void save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.tickets WHERE u.id = :id", User.class)
                    .setParameter("id", id)
                    .uniqueResult();
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list(); // Use the entity name
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

   /* public void updateUserTickets(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(user); // Cascade will handle tickets
            transaction.commit();
            System.out.println("User and tickets updated successfully.");
        } catch (Exception e) {
            if (transaction != null && transaction.getStatus().canRollback()) transaction.rollback();
            System.err.println("Transaction rolled back due to an error: " + e.getMessage());
            e.printStackTrace();
        }
    }*/

    @Override
    public void delete(int id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}