package com.example.hw9_maktab28.model;

import java.util.Date;
import java.util.UUID;

public class Task {
        private String title;
        private String description;
        private Date date;
        private State state;
        private UUID uuid;
        private UUID userID ;


    public Task(String title, String description , State state , UUID userID) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.uuid = UUID.randomUUID();
        this.userID = userID;
    }
    public Task(){
        this.uuid = UUID.randomUUID();
    }

    public Task(UUID uuid) {
        this.uuid = uuid;
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

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID){
        this.userID = userID;
    }

    public State getState() {
        return state;
    }





}
