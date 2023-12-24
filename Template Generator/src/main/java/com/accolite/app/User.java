package com.accolite.app;

public class User {
    private  int id;
    private String name;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(int l, String name) {
        this.id = l;
        this.name = name;
    }
}
