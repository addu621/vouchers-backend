package com.example.server.repositories;

import com.example.server.entities.VoucherCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherCompanyRepo extends JpaRepository<VoucherCompany,Long> {

//    @Query(value = "select count(*) from voucher_company where name=?1")
    public VoucherCompany findByName(String company);
}
