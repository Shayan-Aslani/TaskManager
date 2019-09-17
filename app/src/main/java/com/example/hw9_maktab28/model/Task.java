package com.example.hw9_maktab28.model;

import java.util.Date;
import java.util.UUID;

public class Task {
        private String title;
        private String description;
        private Date date;
        private State state;
        private UUID uuid;


    public Task(String title, String description , State state) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.uuid = UUID.randomUUID();
    }
    public Task(){
        this.uuid = UUID.randomUUID();
    }

    public void setState(State state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getId() {
            return uuid;
        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }





}
