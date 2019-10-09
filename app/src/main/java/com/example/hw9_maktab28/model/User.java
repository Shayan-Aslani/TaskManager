package com.example.hw9_maktab28.model;

import java.util.UUID;

public class User {

    private String username;
    private String password;
    private Role role;
    private UUID userId;

    public User(UUID uuid) {
        userId = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(String username, String password , Role role) {
        this.username = username;
        this.password = password;
        this.userId = UUID.randomUUID();
        this.role = role;
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
