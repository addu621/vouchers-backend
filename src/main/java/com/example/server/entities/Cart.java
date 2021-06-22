package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cart")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long id;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "buyer_id")
    private long buyerId;
}
