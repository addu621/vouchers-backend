package com.example.server.entities;

import com.example.server.enums.AmountTransferStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "amount_transfers")
public class AmountTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transfer_id")
    private long id;

    @Column(name = "order_item_id")
    private long orderItemId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "buyer_amount")
    private BigDecimal buyerAmount;

    @Column(name = "seller_amount")
    private BigDecimal sellerAmount;

    @Column(name = "commission_earned")
    private BigDecimal commissionEarned;

    @Column(name = "amount_transfer_status")
    private AmountTransferStatus amountTransferStatus;

    @CreationTimestamp
    private Date createdDate;
}
