package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long issueId;

    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "voucher_id")
    private Long voucherId;

    @Column
    private String comment;

}
