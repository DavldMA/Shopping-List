package com.example.shopping_list_application;

public class User {
   // username / email / password
    String username;
    String email;
    String Password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        Password = password;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
