package com.example.server.entities;

import com.example.server.enums.TransactionStatus;
import com.example.server.enums.TransactionType;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private String id;

    @Column(name = "transaction_date")
    private Date transactionDate;

    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "coins_added")
    private int coinsAddedToWallet = 0;

    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Column(name = "order_id")
    private Long orderId;
}
