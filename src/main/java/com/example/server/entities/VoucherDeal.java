package com.example.server.entities;

import com.example.server.enums.DealStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="voucher_deal")
@Data
public class VoucherDeal {

    @Id
    @Column(name = "deal_id")
    private Long id;

    @Column(name = "status")
    private DealStatus dealStatus;

    @Column(name = "bought_price")
    private BigDecimal boughtPrice;

    @Column(name = "bought_on")
    private String boughtOn;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "voucher_id")
    private Long voucherId;
}
