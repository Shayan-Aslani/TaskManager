package com.example.hw9_maktab28.model;

import java.util.UUID;

public class User {

    private String username;
    private String password;
    private UUID userId;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.userId = UUID.randomUUID();
    }

    public UUID getUserId(){
        return this.userId;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals( user.username) && password.equals(user.password);
    }
}
