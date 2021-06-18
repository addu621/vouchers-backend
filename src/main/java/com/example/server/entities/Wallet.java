package com.example.server.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    private Long person_id;
    private BigDecimal balance;

    @OneToOne
    @MapsId
    @JoinColumn(name = "person_id")
    private Person person;
}
