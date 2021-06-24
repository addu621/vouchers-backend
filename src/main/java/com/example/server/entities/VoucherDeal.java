package com.example.server.entities;

import com.example.server.enums.DealStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="voucher_deal")
@Data
public class VoucherDeal {

    @Id
    @Column(name = "deal_id")
    private Long id;

    @Column(name = "status")
    private DealStatus dealStatus;

    @Column(name = "quoted_price")
    private BigDecimal quotedPrice;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "voucher_id")
    private Long voucherId;
}
