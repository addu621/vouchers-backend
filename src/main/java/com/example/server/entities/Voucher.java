package com.example.server.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucher_id;
    private String voucher_code;
    private BigDecimal listed_price;
    private String created_on;
    private String expiry_date;
    private boolean is_verified;
    private Integer discount;
    private String website_url;

    @ManyToOne
    @JoinColumn(name="seller_id",referencedColumnName="seller_id", nullable=false)
    private SellerProfile seller_profile;

    @OneToOne(mappedBy = "voucher", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private VoucherBuyer voucher_buyer;

    @OneToOne
    @JoinColumn(name="voucher_type", nullable=false)
    private VoucherType voucher_type;

    @OneToOne
    @JoinColumn(name="voucher_company", nullable=false)
    private VoucherCompany voucher_company;

}
