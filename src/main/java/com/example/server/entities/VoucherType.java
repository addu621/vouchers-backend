package com.example.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "voucher_type")
public class VoucherType {
    @Id
    private Long id;
    private String name;
}
