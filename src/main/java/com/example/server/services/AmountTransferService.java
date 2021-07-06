package com.example.server.services;

import com.example.server.entities.AmountTransfer;
import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherOrder;
import com.example.server.entities.VoucherOrderDetail;
import com.example.server.enums.AmountTransferStatus;
import com.example.server.enums.TransactionType;
import com.example.server.repositories.AmountTransferRepository;
import com.example.server.repositories.VoucherOrderDetailRepository;
import com.example.server.repositories.VoucherOrderRepository;
import com.example.server.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AmountTransferService {

    private final AmountTransferRepository transferRepository;
    private final TransactionService transactionService;
    private final VoucherOrderRepository voucherOrderRepository;
    private final VoucherOrderDetailRepository voucherOrderDetailRepository;
    private final VoucherRepository voucherRepository;
    private final Utility utility;

    public AmountTransfer addAmountTransfer(long orderItemId, String transactionId, BigDecimal buyerAmount){
        AmountTransfer amountTransfer = new AmountTransfer();
        amountTransfer.setOrderItemId(orderItemId);
        amountTransfer.setBuyerAmount(buyerAmount);
        amountTransfer.setTransactionId(transactionId);
        BigDecimal commission = buyerAmount.multiply(new BigDecimal(0.10));
        amountTransfer.setSellerAmount(buyerAmount.subtract(commission));
        amountTransfer.setCommissionEarned(commission);
        amountTransfer.setAmountTransferStatus(AmountTransferStatus.RECEIVED_FROM_BUYER);
        return transferRepository.save(amountTransfer);
    }

    public void addAmountTransfersForOrder(long orderId, String transactionId){
        List<VoucherOrderDetail> orders = voucherOrderDetailRepository.findByOrderId(orderId);
        for(VoucherOrderDetail orderDetail:orders){
            this.addAmountTransfer(orderDetail.getId(),transactionId,orderDetail.getItemPrice());
        }
    }

    public List<AmountTransfer> getAllTransfersByStatus(AmountTransferStatus amountTransferStatus){
        return transferRepository.findByAmountTransferStatus(amountTransferStatus);
    }

    public void sendAmountToSeller(long amountTransferId){
        String transactionId = utility.getAlphaNumericString(10);
        AmountTransfer amountTransfer = transferRepository.findById(amountTransferId).get();
        VoucherOrderDetail voucherOrderDetail = voucherOrderDetailRepository.findById(amountTransfer.getOrderItemId()).get();
        Voucher voucher = voucherRepository.findById(voucherOrderDetail.getVoucherId()).get();
        transactionService.addTransaction(transactionId,voucherOrderDetail.getOrderId(),0,0,voucher.getSellerId(), TransactionType.CREDIT,voucherOrderDetail.getItemPrice());
        amountTransfer.setAmountTransferStatus(AmountTransferStatus.SENT_TO_SELLER);
        transferRepository.save(amountTransfer);
    }
}