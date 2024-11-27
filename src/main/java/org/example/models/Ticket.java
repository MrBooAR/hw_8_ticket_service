package org.example.models;

import java.sql.Timestamp;

public class Ticket {
    private int id;
    private int userId;
    private String ticketType;
    private Timestamp creationDate;

    public Ticket(int id, int userId, String ticketType, Timestamp creationDate) {
        this.id = id;
        this.userId = userId;
        this.ticketType = ticketType;
        this.creationDate = creationDate;
    }

    public Ticket(int userId, String ticketType) {
        this.userId = userId;
        this.ticketType = ticketType;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
}