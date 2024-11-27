package org.example.managers;

import org.example.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class TransactionManager {
    public static void updateUserAndTicket(int userId, String newUserName, int ticketId, String newTicketType) {
        try (Connection connection = DatabaseConnection.connect()) {
            // Turn off auto-commit
            connection.setAutoCommit(false);

            // Create Savepoint
            Savepoint savepoint = connection.setSavepoint("BeforeUpdates");

            try {
                // Update user name
                String updateUserQuery = "UPDATE Users SET name = ? WHERE id = ?";
                try (var userStmt = connection.prepareStatement(updateUserQuery)) {
                    userStmt.setString(1, newUserName);
                    userStmt.setInt(2, userId);
                    userStmt.executeUpdate();
                }

                // Update ticket type
                String updateTicketQuery = "UPDATE Tickets SET ticket_type = ?::ticket_type WHERE id = ?";
                try (var ticketStmt = connection.prepareStatement(updateTicketQuery)) {
                    ticketStmt.setString(1, newTicketType);
                    ticketStmt.setInt(2, ticketId);
                    ticketStmt.executeUpdate();
                }

                // Commit transaction if both operations succeed
                connection.commit();
                System.out.println("Transaction committed successfully.");
            } catch (SQLException e) {
                // Roll back to savepoint if an operation fails
                connection.rollback(savepoint);
                System.err.println("Transaction rolled back to savepoint due to an error: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}