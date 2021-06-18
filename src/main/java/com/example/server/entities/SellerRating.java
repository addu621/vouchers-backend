package com.example.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "seller_rating")
public class SellerRating {

    @EmbeddedId
    private SellerBuyerId seller_buyer_id;

    private Integer stars;
    private String comment;

    @OneToOne
    @MapsId("voucher_id")
    @JoinColumn(name = "buyer_id")
    private BuyerProfile buyer_profile;

    @OneToOne
    @MapsId("seller_id")
    @JoinColumn(name = "seller_id")
    private SellerProfile seller_profile;


}
