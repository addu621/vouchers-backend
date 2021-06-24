package com.example.server.services;

import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherDeal;
import com.example.server.entities.VoucherOrder;
import com.example.server.enums.DealStatus;
import com.example.server.enums.OrderStatus;
import com.example.server.repositories.VoucherOrderRepository;
import com.example.server.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class VoucherOrderService {

    private final VoucherOrderRepository voucherOrderRepository;

    private final VoucherRepository voucherRepository;

    public VoucherOrder addOrder(Long buyerId, Long voucherId){

        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setVoucherId(voucherId);
        Voucher voucher = voucherRepository.findById(voucherId).get();

        List<VoucherOrder> voucherDeals = voucherOrderRepository.findByVoucherIdAndOrderStatus(voucherId, OrderStatus.SUCCESS);
        if(voucherDeals.size()==0){
            voucherOrder.setBuyerId(buyerId);
            voucherOrder.setOrderPrice(voucher.getSellingPrice());
            voucherOrder.setOrderStatus(OrderStatus.DISPUTE);
            Date date = new Date();
            voucherOrder.setOrderDate(date);
            return voucherOrderRepository.save(voucherOrder);
        }
        return null;
    }
}
