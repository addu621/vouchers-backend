package com.example.server.services;

import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.VoucherDeal;
import com.example.server.enums.DealStatus;
import com.example.server.repositories.VoucherDealRepository;
import com.example.server.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class VoucherDealService {

    @Autowired
    VoucherDealRepository voucherDealRepository;

    @Autowired
    VoucherRepository voucherRepository;

//    public VoucherDeal buyVoucher(Long buyerId, Long voucherId){
//
//        VoucherDeal voucherDeal = new VoucherDeal();
//        voucherDeal.setVoucherId(voucherId);
//        Voucher voucher = voucherRepository.findById(voucherId).get();
//
//        // check if voucher exists already in the deals table
//        List<VoucherDeal> voucherDeals = voucherDealRepository.findByVoucherIdAndDealStatus(voucherId, DealStatus.BOUGHT);
//        if(voucherDeals.size()==0){
//            // voucher is not bought yet
//            // buy the voucher
//            voucherDeal.setBuyerId(buyerId);
//            voucherDeal.setBoughtPrice(voucher.getSellingPrice());
//            voucherDeal.setDealStatus(DealStatus.BOUGHT);
//            Date date = new Date();
//            voucherDeal.setBoughtOn(date.toString());
//            return voucherDealRepository.save(voucherDeal);
//        }
//
//        return null;
//    }

    public GenericResponse quotePrice(Long buyerId, Long voucherId, BigDecimal quotedPrice){
        GenericResponse genericResponse = new GenericResponse();

        List<VoucherDeal> alreadyExists = voucherDealRepository.findByVoucherIdAndBuyerId(voucherId,buyerId);
        if(alreadyExists.size()==0){
            // create new voucher deal
            VoucherDeal voucherDeal = new VoucherDeal();
            voucherDeal.setQuotedPrice(quotedPrice);
            voucherDeal.setBuyerId(buyerId);
            voucherDeal.setVoucherId(voucherId);
            voucherDeal.setDealStatus(DealStatus.QUOTED);
            VoucherDeal savedVoucher = voucherDealRepository.save(voucherDeal);
            genericResponse.setMessage("You have placed bid for this coupon");
            genericResponse.setStatus(200);
        }
        else{
            // update existing order Deal
            VoucherDeal oldDeal = alreadyExists.get(0);
            oldDeal.setQuotedPrice(quotedPrice);
            oldDeal.setBuyerId(buyerId);
            oldDeal.setVoucherId(voucherId);
            oldDeal.setDealStatus(DealStatus.QUOTED);
            VoucherDeal savedVoucher = voucherDealRepository.save(oldDeal);
            genericResponse.setMessage("Your new bid has been placed bid for this coupon");
            genericResponse.setStatus(200);
        }
    return genericResponse;
    // allow only higher bid than before
    // do not allow bid higher than the selling price of voucher
    }


}
