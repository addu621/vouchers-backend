package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "seller_rating")
@Data
public class SellerRating {
    @Column(name = "rating_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "stars")
    private int stars;

    @Column(name = "comment")
    private String comment;
}
