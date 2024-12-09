package org.example.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true) // Updated to nullable as the JSON has null for some user associations
    private User user;

    @Enumerated(EnumType.STRING) // Changed to STRING for better readability in the database
    @Column(name = "ticket_type", nullable = false)
    private TicketType ticketType;

    @Column(name = "ticket_class", nullable = true)
    private String ticketClass; // Matches "ticketClass" in JSON

    @Column(name = "start_date", nullable = true)
    private LocalDate startDate; // Matches "startDate" in JSON

    @Column(name = "price", nullable = true, precision = 10, scale = 2)
    private BigDecimal price; // Matches "price" in JSON, allows null and decimals

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    public Ticket() {
    }

    public Ticket(User user, TicketType ticketType, String ticketClass, LocalDate startDate, BigDecimal price) {
        this.user = user;
        this.ticketType = ticketType;
        this.ticketClass = ticketClass;
        this.startDate = startDate;
        this.price = price;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public int getUserId() {
        return user != null ? user.getId() : 0; // Handles null user
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setUser(User user) {
        this.user = user;
    }
}