package com.example.server.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @Column(name = "wallet_id")
    private Long id;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "owner_id")
    private Long ownerId;
}
