package com.example.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "buyer_profile")
public class BuyerProfile {

    @Id
    private Long buyer_id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "buyer_id")
    private Person person;

    @OneToOne(mappedBy = "buyer_profile", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SellerRating seller_rating;

    @OneToOne(mappedBy = "buyer_profile", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private VoucherBuyer voucher_buyer;
}
