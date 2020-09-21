package com.almod.DemoSpring.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String anons;
    private String full_text;
    private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    private User usr;

    public Post() {
    }

    public Post(String title, String anons, String full_text) {
        this.title = title;
        this.anons = anons;
        this.full_text = full_text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String fulltext) {
        this.full_text = fulltext;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate(){
        return date;
    }

    public User getUsr() {
        return usr;
    }

    public void setUsr(User user) {
        this.usr = user;
    }
}
