package com.example.server.services;

import com.example.server.dto.request.FilterRequest;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.*;
import com.example.server.enums.DealStatus;
import com.example.server.enums.OrderStatus;
import com.example.server.enums.VoucherVerificationStatus;
import com.example.server.repositories.*;
import lombok.AllArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    private final VoucherOrderRepository voucherOrderRepository;
    private final VoucherOrderDetailRepository voucherOrderDetailRepository;

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

    public List<Voucher> sortByTime(List<Voucher> vouchers){
        vouchers.sort((v1,v2)->v2.getCreatedOn().compareTo(v1.getCreatedOn()));
        return vouchers;
    }

    public List<Voucher> getAllUnverifiedVouchers(){
        List<Voucher> vouchers = this.voucherRepository.findByVerificationStatus(VoucherVerificationStatus.PENDING);
        vouchers = vouchers.stream().filter((Voucher voucher)->!isVoucherSold(voucher.getId())).collect(Collectors.toList());
        return sortByTime(vouchers);
    }

    public boolean isVoucherSold(long voucherId){
        List<VoucherOrderDetail> voucherOrders = (List<VoucherOrderDetail>) this.voucherOrderDetailRepository.findAll();
        return voucherOrders.stream().anyMatch((VoucherOrderDetail vd)->vd.getVoucherId()==voucherId);
    }

    public List<Voucher> getAllVouchers() {
        List<Voucher> vouchers = this.voucherRepository.findAll();
        vouchers =  vouchers.stream().filter((Voucher voucher)->!isVoucherSold(voucher.getId())).collect(Collectors.toList());
        return sortByTime(vouchers);
    }

    public List<Voucher> getAllVerifiedVouchers(){
        List<Voucher> vouchers = this.voucherRepository.findByVerificationStatus(VoucherVerificationStatus.VERIFIED);
        vouchers =  vouchers.stream().filter((Voucher voucher)->!isVoucherSold(voucher.getId())).collect(Collectors.toList());
        return sortByTime(vouchers);
    }

    public List<Voucher> getBuyVouchers(Long userId){
        List<VoucherOrder> voucherOrders = this.voucherOrderRepository.findByBuyerIdAndOrderStatus(userId, OrderStatus.SUCCESS);
        List<Voucher> vouchers = new ArrayList<>();
        voucherOrders.forEach((VoucherOrder v) -> {
            voucherOrderDetailRepository.findByOrderId(v.getId()).forEach((VoucherOrderDetail vd)->{
                Voucher voucher = this.getVoucherById(vd.getVoucherId());
                vouchers.add(voucher);
            });
        });
        return sortByTime(vouchers);
    }

    public List<Voucher> getSellVouchers(Long sellerId){
        return sortByTime(this.voucherRepository
                    .findBySellerId(sellerId)
                    .stream()
                    .filter((Voucher voucher) -> isVoucherSold(voucher.getId()))
                    .collect(Collectors.toList()));
    }

    public List<Voucher> getVouchersBySellerId(long sellerId){
        return sortByTime(this.voucherRepository.findBySellerId(sellerId));
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

    public List<Voucher> filterVouchers(FilterRequest filterRequest) {
        List<Voucher> voucherList = voucherRepository.filterCoupons(filterRequest.getCategories(),filterRequest.getCompanies());
        return sortByTime(voucherList);
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
    public Long getSellerIdByVoucherId(Long voucherId){
        Voucher voucher = voucherRepository.findById(voucherId).get();
        return voucher.getSellerId();
    }
    public BigDecimal getVoucherCostById(Long voucherId){
        Voucher voucher = voucherRepository.findById(voucherId).get();
        return voucher.getVoucherValue();
    }
}
