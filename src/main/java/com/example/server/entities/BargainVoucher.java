package com.example.server.entities;

import com.example.server.enums.BargainVoucherStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "bargain_voucher")
@Data
public class BargainVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bargain_id")
    private Long dealId;

    @CreationTimestamp
    private Date createdOn;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "bargain_price")
    private BigDecimal bargainPrice;

    @Column(name="bargain_status")
    private BargainVoucherStatus bargainStatus;
}
