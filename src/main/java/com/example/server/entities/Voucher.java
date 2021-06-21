package com.example.server.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity

@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voucher_id;
    private BigDecimal voucher_value;
    private String voucher_code;
    private BigDecimal selling_price;
    private String created_on;
    private String expiry_date;
    private boolean is_negotiable;
    private boolean is_verified;
    private String description;
    private String title;

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

    public void setVoucher_id(Long voucher_id) {
        this.voucher_id = voucher_id;
    }

    public void setVoucher_value(BigDecimal voucher_value) {
        this.voucher_value = voucher_value;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public void setSelling_price(BigDecimal selling_price) {
        this.selling_price = selling_price;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public void setIs_negotiable(boolean is_negotiable) {
        this.is_negotiable = is_negotiable;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSeller_profile(SellerProfile seller_profile) {
        this.seller_profile = seller_profile;
    }

    public void setVoucher_buyer(VoucherBuyer voucher_buyer) {
        this.voucher_buyer = voucher_buyer;
    }

    public void setVoucher_type(VoucherType voucher_type) {
        this.voucher_type = voucher_type;
    }

    public void setVoucher_company(VoucherCompany voucher_company) {
        this.voucher_company = voucher_company;
    }

    public void setVoucher_category(VoucherCategory voucher_category) {
        this.voucher_category = voucher_category;
    }

    public Long getVoucher_id() {
        return voucher_id;
    }

    public BigDecimal getVoucher_value() {
        return voucher_value;
    }

    public String getVoucher_code() {
        return voucher_code;
    }

    public Voucher() {
    }

    public BigDecimal getSelling_price() {
        return selling_price;
    }

    public String getCreated_on() {
        return created_on;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public boolean isIs_negotiable() {
        return is_negotiable;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public SellerProfile getSeller_profile() {
        return seller_profile;
    }

    public VoucherBuyer getVoucher_buyer() {
        return voucher_buyer;
    }

    public VoucherType getVoucher_type() {
        return voucher_type;
    }

    public VoucherCompany getVoucher_company() {
        return voucher_company;
    }

    public VoucherCategory getVoucher_category() {
        return voucher_category;
    }

    @OneToOne
    @JoinColumn(name="voucher_category", nullable=false)
    private VoucherCategory voucher_category;

    public Voucher(Long voucher_id, BigDecimal voucher_value, String voucher_code, BigDecimal selling_price, String created_on, String expiry_date, boolean is_negotiable, boolean is_verified, String description, String title, SellerProfile seller_profile, VoucherBuyer voucher_buyer, VoucherType voucher_type, VoucherCompany voucher_company, VoucherCategory voucher_category) {
        this.voucher_id = voucher_id;
        this.voucher_value = voucher_value;
        this.voucher_code = voucher_code;
        this.selling_price = selling_price;
        this.created_on = created_on;
        this.expiry_date = expiry_date;
        this.is_negotiable = is_negotiable;
        this.is_verified = is_verified;
        this.description = description;
        this.title = title;
        this.seller_profile = seller_profile;
        this.voucher_buyer = voucher_buyer;
        this.voucher_type = voucher_type;
        this.voucher_company = voucher_company;
        this.voucher_category = voucher_category;
    }
}
