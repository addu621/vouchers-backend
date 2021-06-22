package com.example.server.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long id;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password",nullable = false)
    private String password;

    @Column
    private String mobile;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    public Person() {

    }

    public Person(Long id, String email, String firstName, String middleName, String lastName, String password, String mobile, String imageUrl, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
        this.mobile = mobile;
        this.imageUrl = imageUrl;
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "Person{" +
                "first_name=" + firstName +
                ", middle_name='" + middleName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", image_url='" + imageUrl + '\'' +
                '}';
    }
}
