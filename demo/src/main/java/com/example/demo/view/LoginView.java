package com.example.demo.view;

import com.example.demo.database.DatabaseHandler;

import javax.inject.Named;

@Named
public class LoginView {

    private String username;
    private String password;

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


}


