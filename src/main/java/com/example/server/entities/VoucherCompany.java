package com.example.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "voucher_company")
public class VoucherCompany {
    @Id
    private Long id;
    private String name;
}
