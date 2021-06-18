package com.example.server.entities;

import javax.persistence.*;

@Entity
@Table(name="seller_profile")
public class SellerProfile {
    @Id
    private Long seller_id;
    private String is_verified;

    @OneToOne
    @MapsId
    @JoinColumn(name = "seller_id")
    private Person person;

    @OneToOne(mappedBy = "seller_profile", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SellerRating seller_rating;


}
