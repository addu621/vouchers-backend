package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "total_price")
    private BigDecimal totalPrice;
}
