package com.example.hw9_maktab28.model;

import com.example.hw9_maktab28.greendao.StateConverter;
import com.example.hw9_maktab28.greendao.UUIDConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;
import java.util.UUID;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "Task")
public class Task {

    @Id(autoincrement = true)
    private Long _id;
    @Property(nameInDb = "title")
    private String title;
    @Property(nameInDb = "description")
    private String description;
    @Property(nameInDb = "date")
    private Date date;
    @Property(nameInDb = "state")
    @Convert(converter = StateConverter.class , columnType = Integer.class)
    private State state;
    @Property(nameInDb = "uuid")
    @Index(unique = true)
    @Convert(converter = UUIDConverter.class, columnType = String.class)
    private UUID uuid;
    @Property(nameInDb = "userId")
    @Convert(converter = UUIDConverter.class, columnType = String.class)
    private UUID userID;
    @Property(nameInDb = "imagePath")
    private String imagePath ;


    public Task(String title, String description, State state, UUID userID) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.uuid = UUID.randomUUID();
        this.userID = userID;
    }

    public Task() {
        this.uuid = UUID.randomUUID();
    }


    public String getPhotoName() {
        return "IMG_" + getId() + ".jpg";
    }

    public Task(UUID uuid) {
        this.uuid = uuid;
    }

    @Generated(hash = 462481842)
    public Task(Long _id, String title, String description, Date date, State state,
            UUID uuid, UUID userID, String imagePath) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.state = state;
        this.uuid = uuid;
        this.userID = userID;
        this.imagePath = imagePath;
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

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public State getState() {
        return state;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


}
