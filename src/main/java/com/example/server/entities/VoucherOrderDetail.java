package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Table(name = "voucher_order_detail")
@Entity
public class VoucherOrderDetail{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "item_price")
    private BigDecimal itemPrice;
}
