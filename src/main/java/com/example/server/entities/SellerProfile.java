package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="seller_profile")
@Data
public class SellerProfile {

    @Id
    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "is_verified")
    private String isVerified;
}
