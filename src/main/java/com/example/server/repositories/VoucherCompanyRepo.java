package com.example.server.repositories;

import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherCompanyRepo extends JpaRepository<VoucherCompany,Long> {

//    @Query(value = "select count(*) from voucher_company where name=?1")
    public VoucherCompany findByName(String company);

    @Query("SELECT vc from VoucherCompany as vc Right Join CategoryCompany as cc on vc.id = cc.companyId and cc.categoryId = :category")
    List<VoucherCompany> companiesInCategory(@Param("category") Long categoryId);

}
