package com.example.server.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long person_id;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    @Column
    private String first_name;

    @Column
    private String middle_name;

    @Column
    private String last_name;

    @Column(name = "password",nullable = false)
    private String password;

    @Column
    private String mobile;

    @Column
    private String image_url;

    public Person() {

    }

    public Person(String first_name, String middle_name, String last_name, String email, String password, String mobile, String image_url, Wallet wallet, BuyerProfile buyer_profile, SellerProfile seller_profile) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.image_url = image_url;
    }


    @Override
    public String toString() {
        return "Person{" +
                "first_name=" + first_name +
                ", middle_name='" + middle_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
