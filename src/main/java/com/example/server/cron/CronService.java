package com.example.server.cron;

import com.example.server.entities.AmountTransfer;
import com.example.server.entities.VoucherOrder;
import com.example.server.entities.VoucherOrderDetail;
import com.example.server.enums.AmountTransferStatus;
import com.example.server.repositories.AmountTransferRepository;
import com.example.server.repositories.VoucherOrderDetailRepository;
import com.example.server.repositories.VoucherOrderRepository;
import com.example.server.services.AmountTransferService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class CronService {

    private final AmountTransferService amountTransferService;
    private final VoucherOrderDetailRepository voucherOrderDetailRepository;
    private final VoucherOrderRepository voucherOrderRepository;
    private final AmountTransferRepository amountTransferRepository;

    public boolean isTimeOver(Date orderDate){
        long diff = new Date().getTime() - orderDate.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        int diffInDays = (int) ((new Date().getTime() - orderDate.getTime()) / (1000 * 60 * 60 * 24));
        return diffMinutes>=2;
    }

    public Date getOrderDateByAmountTransferId(long id){
        AmountTransfer amountTransfer = amountTransferRepository.findById(id).get();
        VoucherOrderDetail voucherOrderDetail = voucherOrderDetailRepository.findById(amountTransfer.getOrderItemId()).get();
        VoucherOrder voucherOrder = voucherOrderRepository.findById(voucherOrderDetail.getOrderId()).get();
        return voucherOrder.getOrderDate();
    }

    @Scheduled(cron = " 1 * * * * *")
    //@Scheduled(cron = "2 2 */2 * *")
    public void checkAmountTransfer(){
        System.out.println("CRON job triggered!");
        List<AmountTransfer> amountTransfers = amountTransferService.getAllTransfersByStatus(AmountTransferStatus.RECEIVED_FROM_BUYER);
        for(AmountTransfer amountTransfer : amountTransfers){
            Date orderDate  = getOrderDateByAmountTransferId(amountTransfer.getId());
            if(isTimeOver(orderDate)){
                amountTransferService.sendAmountToSeller(amountTransfer.getId());
            }
        }
    }
}
