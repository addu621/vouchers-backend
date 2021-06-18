package com.example.server.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="voucher_buyer")
public class VoucherBuyer {

    @Id
    private Long voucher_id;
    private Number status;
    private BigDecimal bought_price;
    private String bought_on;

    @OneToOne
    @JoinColumn(name = "buyer_id")
    private BuyerProfile buyer_profile;

    @OneToOne
    @MapsId
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
}
