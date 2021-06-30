package com.example.server.entities;

import com.example.server.enums.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="voucher_order")
@Data
public class VoucherOrder {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "order_status")
    private OrderStatus orderStatus;
}
