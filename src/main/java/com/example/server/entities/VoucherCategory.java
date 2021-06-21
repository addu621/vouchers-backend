package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "voucher_category")
@Data
public class VoucherCategory {
    @Id
    private Long id;
    private String name;
}
