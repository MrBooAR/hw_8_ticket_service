package org.example.models;

import javax.persistence.*;

import java.sql.Timestamp;

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

    @Column(name = "creation_date", nullable = false, updatable = false)
    private Timestamp creationDate;

    public Ticket() {
    }

    public Ticket(User user, TicketType ticketType) {
        this.user = user;
        this.ticketType = ticketType;
        this.creationDate = new Timestamp(System.currentTimeMillis());
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public void setUser(User user) {
        this.user = user;
    }
}