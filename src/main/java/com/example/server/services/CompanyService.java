package com.example.server.services;

import com.example.server.entities.VoucherCompany;
import com.example.server.repositories.VoucherCompanyRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService {

    private final VoucherCompanyRepo voucherCompanyRepo;

    public VoucherCompany getCompanyById(long id){
        return this.voucherCompanyRepo.getById(id);
    }

    public List<VoucherCompany> getCompanyInCategory(Long categoryId){
        return voucherCompanyRepo.companiesInCategory(categoryId);
    }
    public List<VoucherCompany> getCompanyInCategories(List<Long> categoryIdList){
        return voucherCompanyRepo.companiesInCategories(categoryIdList);
    }


}
