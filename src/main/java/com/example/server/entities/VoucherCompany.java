package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "voucher_company")
@Data
public class VoucherCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(mappedBy = "voucher_type", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Voucher voucher;
}
