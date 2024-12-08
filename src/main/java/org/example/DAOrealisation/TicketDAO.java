package org.example.DAOrealisation;

import org.example.interfaces.DAO;
import org.example.database.DatabaseConnection;
import org.example.models.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements DAO<Ticket> {

    @Override
    public void save(Ticket ticket) {
        String query = "INSERT INTO Tickets (user_id, ticket_type) VALUES (?, ?::ticket_type)";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ticket.getUserId());
            statement.setString(2, ticket.getTicketType());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ticket getById(int id) {
        String query = "SELECT * FROM Tickets WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Ticket(resultSet.getInt("id"), resultSet.getInt("user_id"),
                        resultSet.getString("ticket_type"), resultSet.getTimestamp("creation_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ticket> getAll() {
        String query = "SELECT * FROM Tickets";
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DatabaseConnection.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                tickets.add(new Ticket(resultSet.getInt("id"), resultSet.getInt("user_id"),
                        resultSet.getString("ticket_type"), resultSet.getTimestamp("creation_date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public void update(Ticket ticket) {
        String query = "UPDATE Tickets SET ticket_type = ?::ticket_type WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, ticket.getTicketType());
            statement.setInt(2, ticket.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Tickets WHERE id = ?";
        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}