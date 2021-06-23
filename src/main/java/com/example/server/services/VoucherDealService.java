package com.example.server.services;

import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherDeal;
import com.example.server.enums.DealStatus;
import com.example.server.repositories.VoucherDealRepository;
import com.example.server.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;

@AllArgsConstructor
@Service
public class VoucherDealService {

    @Autowired
    VoucherDealRepository voucherDealRepository;

    @Autowired
    VoucherRepository voucherRepository;

    public VoucherDeal buyVoucher(Long buyerId, Long voucherId){

        VoucherDeal voucherDeal = new VoucherDeal();
        voucherDeal.setVoucherId(voucherId);
        Voucher voucher = voucherRepository.findById(voucherId).get();

        // check if voucher exists already in the deals table
        List<VoucherDeal> voucherDeals = voucherDealRepository.findByVoucherIdAndDealStatus(voucherId, DealStatus.BOUGHT)
        if(voucherDeals.size()==0){
            // voucher is not bought yet
            // buy the voucher
            voucherDeal.setBuyerId(buyerId);
            voucherDeal.setBoughtPrice(voucher.getSellingPrice());
            voucherDeal.setDealStatus(DealStatus.BOUGHT);

            voucherDeal.setBoughtOn(new Date(););
            return voucherDealRepository.save(voucherDeal);
        }

        return null;
    }
}
