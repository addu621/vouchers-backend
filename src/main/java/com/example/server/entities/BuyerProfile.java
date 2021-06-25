package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "buyer_profile")
@Data
public class BuyerProfile {

    @Id
    @Column(name = "buyer_id")
    private Long buyerId;
}
