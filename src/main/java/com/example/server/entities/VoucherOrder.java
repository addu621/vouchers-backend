package com.example.server.entities;

import com.example.server.enums.OrderStatus;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name="voucher_order")
public class VoucherOrder {

    @Id
    @Column(name = "order_id")
    private Long id;

    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "order_price")
    private BigDecimal orderPrice;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "order_status")
    private OrderStatus orderStatus;
}
