package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "item_price")
    private BigDecimal itemPrice;
}
