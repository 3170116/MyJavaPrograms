/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MailApp;

/**
 *
 * @author Jimis
 */

import java.sql.*;

public class User {
    private String username;
    private String password;
    private Mail[] newMails;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.newMails = null;
    }

    public User() {
        this(null,null);
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

    public Mail[] getNewMails() {
        return newMails;
    }
}

