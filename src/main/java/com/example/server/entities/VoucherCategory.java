package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "voucher_category")
@Data
public class VoucherCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
