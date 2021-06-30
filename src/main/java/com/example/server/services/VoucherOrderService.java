package com.example.server.services;

import com.example.server.entities.*;
import com.example.server.enums.DealStatus;
import com.example.server.enums.OrderStatus;
import com.example.server.enums.TransactionType;
import com.example.server.repositories.TransactionRepository;
import com.example.server.repositories.VoucherOrderDetailRepository;
import com.example.server.repositories.VoucherOrderRepository;
import com.example.server.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class VoucherOrderService {

    private final VoucherOrderRepository voucherOrderRepository;
    private final VoucherOrderDetailRepository voucherOrderDetailRepository;
    private final TransactionService transactionService;
    private final WalletService walletService;

    private final VoucherRepository voucherRepository;
    private final TransactionRepository transactionRepository;

    public VoucherOrder createOrder(Long buyerId){
        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setBuyerId(buyerId);
        voucherOrder.setOrderStatus(OrderStatus.SUCCESS);
        Date date = new Date();
        voucherOrder.setOrderDate(date);
        return voucherOrderRepository.save(voucherOrder);
    }

    public VoucherOrderDetail addOrderItem(Long orderId, Long voucherId, BigDecimal price){
        VoucherOrderDetail voucherOrderDetail = new VoucherOrderDetail();
        voucherOrderDetail.setOrderId(orderId);
        voucherOrderDetail.setVoucherId(voucherId);
        voucherOrderDetail.setItemPrice(price);
        voucherOrderDetail.setVoucherId(voucherId);
        return this.voucherOrderDetailRepository.save(voucherOrderDetail);
    }

    public VoucherOrderDetail addOrderItem(Long orderId, Long voucherId){
        Voucher voucher = this.voucherRepository.findById(voucherId).get();
        VoucherOrderDetail voucherOrderDetail = new VoucherOrderDetail();
        voucherOrderDetail.setOrderId(orderId);
        voucherOrderDetail.setVoucherId(voucherId);
        voucherOrderDetail.setItemPrice(voucher.getSellingPrice());
        voucherOrderDetail.setVoucherId(voucherId);
        return this.voucherOrderDetailRepository.save(voucherOrderDetail);
    }

    public boolean placeOrder(long orderId, String transactionId){
        if(this.voucherOrderRepository.findById(orderId)==null) return false;
        VoucherOrder voucherOrder = this.voucherOrderRepository.findById(orderId).get();
        voucherOrder.setOrderStatus(OrderStatus.SUCCESS);
        BigDecimal totalPrice = new BigDecimal(0);
        List<VoucherOrderDetail> orders = this.voucherOrderDetailRepository.findByOrderId(voucherOrder.getId());
        for(VoucherOrderDetail orderDetail : orders){
            totalPrice = totalPrice.add(orderDetail.getItemPrice());
        }
        int coins = totalPrice.intValue()*5/100;
        transactionService.addTransaction(transactionId,orderId,coins,voucherOrder.getBuyerId(),TransactionType.ORDER_PLACED,totalPrice);
        walletService.addCoinsToWallet(voucherOrder.getBuyerId(),coins);
        return true;
    }
}
