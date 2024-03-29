package com.example.shopping_list_application;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    public int id;
    @ColumnInfo(name="username")
    public String username;
    @ColumnInfo(name="email")
    public String email;

    @ColumnInfo(name="isLogged")
    public boolean isLogged;

    public User(String username, String email) {
        super();
        this.username = username;
        this.email = email;
        this.isLogged = true;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
