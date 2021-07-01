package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "category_company")
@Data
public class CategoryCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "company_id")
    private Long companyId;
}
