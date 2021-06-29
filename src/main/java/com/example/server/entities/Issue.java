package com.example.server.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long issueId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "voucher_id")
    private Long voucherId;

    @Column
    private String comment;

    @Column(name = "is_read")
    private Boolean isRead = false;

    @Column(name = "is_closed")
    private Boolean isClosed = false;

    @CreationTimestamp
    private Date issueDate;

}
