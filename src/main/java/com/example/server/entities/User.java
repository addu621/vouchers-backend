package com.example.server.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_details")
public class User {

    @Id
    @Column(name = "email",unique = true,nullable = false)
    private String userEmail;

    @Column(name = "password",nullable = false)
    private String userPassword;

    public User(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }


}
