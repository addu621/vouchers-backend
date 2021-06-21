package com.example.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "voucher_type")
public class VoucherType {
    @Id
    private Long id;
    private String name;

    @OneToOne(mappedBy = "voucher_type", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Voucher voucher;
}
