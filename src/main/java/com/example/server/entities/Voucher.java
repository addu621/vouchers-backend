package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "voucher")
@Data
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id")
    private Long id;

    @Column(name = "voucher_value")
    private BigDecimal voucherValue;

    @Column(name = "voucher_code")
    private String voucherCode;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "is_negotiable")
    private boolean isNegotiable;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "seller_profile")
    private Long sellerId;

    @Column(name = "voucher_buyer")
    private Long buyerId;

    @Column(name = "voucher_type")
    private Long voucher_type;

    @Column(name = "voucher_company")
    private Long companyId;

    @Column(name = "voucher_category")
    private Long categoryId;
}
