package com.example.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @EmbeddedId
    private CartId cart_id;
    private String created_on;

    @ManyToOne
    @MapsId("voucher_id")
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @ManyToOne
    @MapsId("buyer_id")
    @JoinColumn(name = "buyer_id")
    private BuyerProfile buyer_profile;

}
