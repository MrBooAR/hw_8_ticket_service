package org.example.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "ticket_type", nullable = false)
    private TicketType ticketType;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    public Ticket() {
    }

    public Ticket(User user, TicketType ticketType) {
        this.user = user;
        this.ticketType = ticketType;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return user.getId(); // Access the ID of the related User
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public void setUser(User user) {
        this.user = user;
    }
}