package com.example.server.entities;

import com.example.server.enums.VoucherVerificationStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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
    private Date createdOn;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "is_negotiable")
    private boolean isNegotiable;

    @Column(name = "verification_status")
    private VoucherVerificationStatus verificationStatus;

    @Column(name = "description")
    private String description;

    @Column(name = "title")
    private String title;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "voucher_type")
    private Long voucher_type;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "voucher_company")
    private Long companyId;

    @Column(name = "voucher_category")
    private Long categoryId;
}
