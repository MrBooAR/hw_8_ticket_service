package org.example.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private Timestamp creationDate;

    @Column(nullable = false)
    private String status; // New field for user status

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

    public User() {
    }

    public User(String name) {
        this.name = name;
        this.creationDate = new Timestamp(System.currentTimeMillis());
        this.status = "INACTIVE"; // Default status when a user is created
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status; // Set user status
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets.clear();
        if (tickets != null) {
            tickets.forEach(ticket -> ticket.setUser(this)); // Ensure bidirectional mapping
            this.tickets.addAll(tickets);
        }
    }

    // Add Ticket
    public void addTicket(Ticket ticket) {
        ticket.setUser(this); // Ensure bidirectional mapping
        this.tickets.add(ticket);
    }

    // Remove Ticket
    public void removeTicket(Ticket ticket) {
        ticket.setUser(null); // Break bidirectional mapping
        this.tickets.remove(ticket);
    }
}