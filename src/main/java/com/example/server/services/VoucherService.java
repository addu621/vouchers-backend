package com.example.server.services;

import com.example.server.dto.request.FilterRequest;
import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.response.SellerRatingResponse;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.*;
import com.example.server.enums.DealStatus;
import com.example.server.enums.NotificationType;
import com.example.server.enums.OrderStatus;
import com.example.server.enums.VoucherVerificationStatus;
import com.example.server.model.CheckoutPageCost;
import com.example.server.repositories.*;
import lombok.AllArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private final WalletService walletService;
    private final Utility utilityService;
    private final NotificationService notificationService;

    public GenericResponse saveVoucher(Voucher voucher) {
        GenericResponse genericResponse = new GenericResponse();
        if(!voucherRepository.findByCompanyIdAndVoucherCode(voucher.getCompanyId(),voucher.getVoucherCode()).isEmpty()){
            genericResponse.setMessage("A voucher with same code already exist!");
            genericResponse.setStatus(409);
            return genericResponse;
        }
        voucherRepository.save(voucher);
        genericResponse.setMessage("Voucher Added Successfully!");
        genericResponse.setStatus(201);
        return genericResponse;
    }

    public Voucher getVoucherById(Long id) {
        return voucherRepository.findById(id).get();
    }

    public List<Voucher> searchVoucher(String search){
        List<Voucher> searchResult = voucherRepository.searchVoucher(search);

        return sortByTime(searchResult.stream()
                .filter((Voucher voucher) -> !isVoucherSold(voucher.getId()))
                .collect(Collectors.toList()));
    }

    public List<VoucherCategory> getAllVoucherCategory()
    {
        return voucherCategoryRepository.findAll();
    }

    public List<VoucherCompany> getAllVoucherCompany()
    {
        return voucherCompanyRepo.findAll();
    }

    public List<VoucherCompany> getCompaniesUnderCategories()
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
        List<Voucher> voucherList = voucherRepository.filterCoupons(filterRequest.getCategories(),filterRequest.getCompanies(),filterRequest.getAverageRating(),filterRequest.getIsVerified());
        return sortByTime(voucherList);
    }

    public String acceptVoucher(Long voucherId) throws UnsupportedEncodingException, MessagingException {
        Voucher voucher = voucherRepository.findById(voucherId).get();
        if(voucher==null) {
            return "Voucher not found!!!";
        }
        voucher.setVerificationStatus(VoucherVerificationStatus.VERIFIED);
        voucherRepository.save(voucher);

        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.VOUCHER_APPROVED);
        notification.setVoucherId(voucherId);
        notification.setSellerId(voucher.getSellerId());
        notification.setReceiverId(voucher.getSellerId());
        notification.setTitle("Listing Approved");
        notification.setDescription("Your Listing "+ voucher.getTitle() + " has been approved by our team");
        notificationService.createNewNotification(notification);

        utilityService.voucherAccepted(voucher);

        return "Voucher verified";
    }

    public String rejectVoucher(Long voucherId) throws UnsupportedEncodingException, MessagingException {
        Voucher voucher = voucherRepository.findById(voucherId).get();
        if(voucher==null) {
            return "Voucher not found!!!";
        }
        voucher.setVerificationStatus(VoucherVerificationStatus.REJECTED);
        voucherRepository.save(voucher);

        Notification notification = new Notification();
        notification.setNotificationType(NotificationType.VOUCHER_REJECTED);
        notification.setVoucherId(voucherId);
        notification.setSellerId(voucher.getSellerId());
        notification.setReceiverId(voucher.getSellerId());
        notification.setTitle("Listing Rejected");
        notification.setDescription("Your Listing "+ voucher.getTitle() + " has been rejected our team");
        notificationService.createNewNotification(notification);

        utilityService.voucherRejected(voucher);

        return "Voucher rejected";

    }
    public Long getSellerIdByVoucherId(Long voucherId){
        Voucher voucher = voucherRepository.findById(voucherId).get();
        return voucher.getSellerId();
    }
    public CheckoutPageCost getVoucherCostById(Long voucherId,Long buyerId){
        Voucher voucher = voucherRepository.findById(voucherId).get();
        BigDecimal totalPrice = voucher.getSellingPrice();
        Integer existingCoins = walletService.getWalletById(buyerId).getCoins();
        CheckoutPageCost checkoutPageCost  = utilityService.calculateCheckoutCosts(totalPrice,existingCoins);

        return checkoutPageCost;
    }

    public CheckoutPageCost getVoucherCostById(Long voucherId,Long buyerId,Integer coinsToBeRedeemed){
        Voucher voucher = voucherRepository.findById(voucherId).get();
        BigDecimal totalPrice = voucher.getSellingPrice();
        Integer existingCoins = walletService.getWalletById(buyerId).getCoins();
        CheckoutPageCost checkoutPageCost  = utilityService.calculateCheckoutCosts(totalPrice,existingCoins,coinsToBeRedeemed);

        return checkoutPageCost;
    }

    public Boolean canQuote(Long buyerId,Long voucherId){
        Long sellerId = getSellerIdByVoucherId(voucherId);

        // check if current buyer is not seller
        if(sellerId==buyerId){
            return false;
        }

        // check if voucher is already sold
        if(isVoucherSold(voucherId)){
            return false;
        }

        List<VoucherDeal> alreadyQuotedList = voucherDealRepository.findByVoucherIdAndBuyerId(voucherId,buyerId);
        // if current voucher is not already quoted
        if(alreadyQuotedList.size()==0) {
            return true;
        }

        // check status of already quoted voucher
        VoucherDeal alreadyQuoted = alreadyQuotedList.get(0);
        if(alreadyQuoted.getDealStatus()==DealStatus.REJECTED || alreadyQuoted.getDealStatus()==DealStatus.NONE){
            return true;
        }
        return false;
    }


}
