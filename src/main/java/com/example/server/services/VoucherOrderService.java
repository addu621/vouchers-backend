package com.example.server.services;

import com.example.server.entities.*;
import com.example.server.enums.DealStatus;
import com.example.server.enums.OrderStatus;
import com.example.server.enums.TransactionType;
import com.example.server.model.CheckoutPageCost;
import com.example.server.repositories.TransactionRepository;
import com.example.server.repositories.VoucherOrderDetailRepository;
import com.example.server.repositories.VoucherOrderRepository;
import com.example.server.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoucherOrderService {

    private final VoucherOrderRepository voucherOrderRepository;
    private final VoucherOrderDetailRepository voucherOrderDetailRepository;
    private final TransactionService transactionService;
    private final WalletService walletService;
    private final VoucherService voucherService;
    private final AmountTransferService amountTransferService;
    private final Utility utility;

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

    public boolean placeOrder(long orderId, String transactionId, int noOfCoinsReedemed){
        if(this.voucherOrderRepository.findById(orderId)==null) return false;
        VoucherOrder voucherOrder = this.voucherOrderRepository.findById(orderId).get();
        voucherOrder.setOrderStatus(OrderStatus.SUCCESS);
        BigDecimal totalPrice = new BigDecimal(0);
        List<VoucherOrderDetail> orders = this.voucherOrderDetailRepository.findByOrderId(voucherOrder.getId());
        for(VoucherOrderDetail orderDetail : orders){
            totalPrice = totalPrice.add(orderDetail.getItemPrice());
        }
        Wallet wallet = walletService.getWalletById(voucherOrder.getBuyerId());

        CheckoutPageCost checkoutPageCost = utility.calculateCheckoutCosts(totalPrice, wallet.getCoins(),noOfCoinsReedemed);

        int coinsAdded = checkoutPageCost.getLoyaltyCoinsEarned();
        int coinsDeducted  = noOfCoinsReedemed;
        BigDecimal finalCost = checkoutPageCost.getFinalCostAfterCoinRedeem();
        int walletCoins = checkoutPageCost.getCoinBalanceAfterRedemption();

        System.out.println(checkoutPageCost);

        walletService.setCoinsInWallet(wallet.getId(),walletCoins+coinsAdded);
        transactionService.addTransaction(transactionId,orderId,coinsAdded, coinsDeducted,voucherOrder.getBuyerId(),TransactionType.DEBIT,finalCost);
        amountTransferService.addAmountTransfersForOrder(orderId,transactionId);
        return true;
    }

    public List<VoucherOrderDetail> getBuyOrders(Long userId){
        List<VoucherOrder> voucherOrders = this.voucherOrderRepository.findByBuyerIdAndOrderStatus(userId, OrderStatus.SUCCESS);
        List<VoucherOrderDetail> voucherOrderDetails = new ArrayList<>();
        for(VoucherOrder v:voucherOrders){
            voucherOrderDetails.addAll(voucherOrderDetailRepository.findByOrderId(v.getId()));
        }
        voucherOrderDetails.sort((x,y)->voucherOrderRepository.findById(y.getOrderId()).get().getOrderDate().compareTo(voucherOrderRepository.findById(y.getOrderId()).get().getOrderDate()));
        return voucherOrderDetails;
    }

    public List<VoucherOrderDetail> getSellOrders(long userId){
        List<Voucher> sellVouchers = this.voucherService.getSellVouchers(userId);
        List<VoucherOrderDetail> sellOrders = new ArrayList<>();
        sellVouchers.forEach((Voucher v)->{
            sellOrders.addAll(voucherOrderDetailRepository.findByVoucherId(v.getId()));
        });
        sellOrders.sort((x,y)->voucherOrderRepository.findById(y.getOrderId()).get().getOrderDate().compareTo(voucherOrderRepository.findById(y.getOrderId()).get().getOrderDate()));
        return sellOrders;
    }

    public List<VoucherOrderDetail> getBuyOrdersByOrderId(Long orderId){
        return voucherOrderDetailRepository.findByOrderId(orderId);
    }

    public int getNoOfDisputesByUserId(long userId){
        return 0;
    }
}
