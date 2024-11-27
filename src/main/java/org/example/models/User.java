package org.example.models;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private Timestamp creationDate;

    public User(int id, String name, Timestamp creationDate) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
    }

    public User(String name) {
        this.name = name;
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

    public void setName(String name) {
        this.name = name;
    }
}
