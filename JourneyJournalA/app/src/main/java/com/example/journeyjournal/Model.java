package com.example.journeyjournal;

public class Model {
    private int id;
    private byte[]proavatar;
    private String username;
    private String date;
    private String description;

    //constructor

    public Model(int id, byte[] proavatar, String username, String description, String date) {
        this.id = id;
        this.proavatar = proavatar;
        this.username = username;
        this.description = description;
        this.date = date;
    }
    //getter and setter method

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getProavatar() {
        return proavatar;
    }

    public void setProavatar(byte[] proavatar) {
        this.proavatar = proavatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}