package com.example.server.services;

import com.example.server.entities.VoucherCompany;
import com.example.server.repositories.VoucherCompanyRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {

    private final VoucherCompanyRepo voucherCompanyRepo;

    public VoucherCompany getCompanyById(long id){
        return this.voucherCompanyRepo.getById(id);
    }
}
