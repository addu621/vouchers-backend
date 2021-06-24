package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
@Data
public class Wallet {
    @Id
    @Column(name = "wallet_id")
    private Long id;

    @Column(name = "balance")
    private BigDecimal balance;
}
