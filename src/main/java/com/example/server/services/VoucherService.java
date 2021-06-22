package com.example.server.services;

import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherCategory;
import com.example.server.entities.VoucherCompany;
import com.example.server.entities.VoucherType;
import com.example.server.enums.VoucherVerificationStatus;
import com.example.server.repositories.VoucherCategoryRepo;
import com.example.server.repositories.VoucherCompanyRepo;
import com.example.server.repositories.VoucherRepository;
import com.example.server.repositories.VoucherTypeRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherCategoryRepo voucherCategoryRepository;
    private final VoucherCompanyRepo voucherCompanyRepo;
    private final VoucherTypeRepo voucherTypeRepo;

    public Voucher saveVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public Voucher getVoucherById(Long id) {
        return voucherRepository.findById(id).get();
    }

    public List<Voucher> searchVoucher(String search){
        List<Voucher> searchResult = voucherRepository.searchVoucher(search);
        return searchResult;
    }

    public List<Voucher> getAllVouchers() {
        List<Voucher> vouchers = (List<Voucher>) voucherRepository.findAll();
        return vouchers;
    }

    public List<VoucherCategory> getAllVoucherCategory()
    {
        return voucherCategoryRepository.findAll();
    }

    public List<VoucherCompany> getAllVoucherCompany()
    {
        return voucherCompanyRepo.findAll();
    }

    public List<VoucherType> getAllVoucherType()
    {
        return voucherTypeRepo.findAll();
    }

    public List<Voucher> getAllUnverifiedVouchers(){
        return this.voucherRepository.findByVerificationStatus(VoucherVerificationStatus.PENDING);
    }

    public String addCompany(String company) {
        VoucherCompany voucherCompany= voucherCompanyRepo.findByName(company);
        if(voucherCompany!=null)
        {
            return "Company already exists";
        }
        VoucherCompany newCompany = new VoucherCompany();
        newCompany.setName(company);
        voucherCompanyRepo.save(newCompany);
        return "Company: " + company + " added";
    }

//    var val = cityService.findById(id2);
//        if (val.isPresent()) {
//        System.out.println(val.get());
//    } else {
//        System.out.printf("No city found with id %d%n", id2);
//    }

    public String acceptVoucher(Voucher voucher) {
        voucher.setVerificationStatus(VoucherVerificationStatus.VERIFIED);
        return "Voucher id: " + voucher.getId() + " is accepted";
    }

    public String rejectVoucher(Voucher voucher) {
        voucher.setVerificationStatus(VoucherVerificationStatus.REJECTED);
        return "Voucher id: " + voucher.getId() + " is rejected";
    }
}
