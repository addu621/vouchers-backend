package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "voucher_type")
@Data
public class VoucherType {
    @Id
    private Long id;
    private String name;
}
