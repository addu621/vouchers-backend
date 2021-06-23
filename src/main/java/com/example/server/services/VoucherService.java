package com.example.server.services;

import com.example.server.dto.request.FilterRequest;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.*;
import com.example.server.enums.DealStatus;
import com.example.server.enums.VoucherVerificationStatus;
import com.example.server.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherCategoryRepo voucherCategoryRepository;
    private final VoucherCompanyRepo voucherCompanyRepo;
    private final VoucherTypeRepo voucherTypeRepo;
    private final VoucherDealRepository voucherDealRepository;

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

    public boolean isVoucherSold(long voucherId){
        List<VoucherDeal> voucherDeals = this.voucherDealRepository.findByDealStatus(DealStatus.BOUGHT);
        return voucherDeals.stream().anyMatch((VoucherDeal vd)->vd.getVoucherId()==voucherId);
    }

    public List<Voucher> getAllVouchers() {
        List<Voucher> vouchers = this.voucherRepository.findAll();
        return vouchers.stream().filter((Voucher voucher)->!isVoucherSold(voucher.getId())).collect(Collectors.toList());
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
        List<Voucher> vouchers = this.voucherRepository.findByVerificationStatus(VoucherVerificationStatus.PENDING);
        return vouchers.stream().filter((Voucher voucher)->!isVoucherSold(voucher.getId())).collect(Collectors.toList());
    }

    public List<Voucher> getAllVerifiedVouchers(){
        List<Voucher> vouchers = this.voucherRepository.findByVerificationStatus(VoucherVerificationStatus.VERIFIED);
        return vouchers.stream().filter((Voucher voucher)->!isVoucherSold(voucher.getId())).collect(Collectors.toList());
    }

    public List<Voucher> getBuyVouchers(Long userId){
        List<VoucherDeal> voucherDeals = this.voucherDealRepository.findByBuyerIdAndDealStatus(userId, DealStatus.BOUGHT);
        List<Voucher> vouchers = new ArrayList<>();
        voucherDeals.forEach((VoucherDeal v) -> {
            Voucher voucher = this.getVoucherById(v.getVoucherId());
            vouchers.add(voucher);
        });
        return vouchers;
    }

    public List<Voucher> getSellVouchers(Long sellerId){
        return this.voucherRepository.findBySellerId(sellerId).stream().filter((Voucher voucher) ->{
            List<VoucherDeal> voucherDeals = voucherDealRepository.findByVoucherIdAndDealStatus(voucher.getId(),DealStatus.BOUGHT);
            return !voucherDeals.isEmpty();
        }).collect(Collectors.toList());
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

    public String acceptVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).get();
        if(voucher==null) {
            return "Voucher not found!!!";
        }
        voucher.setVerificationStatus(VoucherVerificationStatus.VERIFIED);
        voucherRepository.save(voucher);
        return "Voucher verified";
    }

    public String rejectVoucher(Long voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).get();
        if(voucher==null) {
            return "Voucher not found!!!";
        }
        voucher.setVerificationStatus(VoucherVerificationStatus.REJECTED);
        voucherRepository.save(voucher);
        return "Voucher rejected";
    }
}
