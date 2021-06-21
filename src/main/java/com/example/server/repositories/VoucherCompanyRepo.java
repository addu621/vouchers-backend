package com.example.server.repositories;

import com.example.server.entities.VoucherCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherCompanyRepo extends JpaRepository<VoucherCompany,String> {
}
