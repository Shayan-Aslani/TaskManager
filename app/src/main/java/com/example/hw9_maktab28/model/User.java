package com.example.hw9_maktab28.model;

import androidx.core.net.ConnectivityManagerCompat;

import com.example.hw9_maktab28.greendao.RoleConverter;
import com.example.hw9_maktab28.greendao.UUIDConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;

@Entity(
        nameInDb = "User"
)
public class User {

    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "username")
    private String username;
    @Property(nameInDb = "password")
    private String password;
    @Property(nameInDb = "role")
    @Convert(converter = RoleConverter.class , columnType = Integer.class)
    private Role role;
    @Property(nameInDb = "date")
    private Date date ;
    @Property(nameInDb = "uuid")
    @Index(unique = true)
    @Convert(converter = UUIDConverter.class, columnType = String.class)
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
        this.date = new Date();
    }

    @Generated(hash = 472887190)
    public User(Long _id, String username, String password, Role role, Date date,
            UUID userId) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.date = date;
        this.userId = userId;
    }

    @Generated(hash = 586692638)
    public User() {
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

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
