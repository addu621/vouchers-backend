package com.example.server.dto.transformer;

import com.example.server.dto.response.OrderResponse;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.Transaction;
import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherOrder;
import com.example.server.entities.VoucherOrderDetail;
import com.example.server.repositories.VoucherOrderRepository;
import com.example.server.repositories.VoucherRepository;
import com.example.server.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@AllArgsConstructor
public class OrderTransformer {
    private final VoucherOrderRepository voucherOrderRepository;
    private final TransactionService transactionService;
    private final VoucherRepository voucherRepository;
    private final VoucherTransformer voucherTransformer;

    public OrderResponse convertEntityToResponse(VoucherOrderDetail voucherOrderDetail){
        OrderResponse orderResponse = new OrderResponse();
        copyProperties(voucherOrderDetail,orderResponse);
        orderResponse.setOrderPrice(voucherOrderDetail.getItemPrice());
        orderResponse.setOrderItemId(voucherOrderDetail.getId());

        VoucherOrder voucherOrder = voucherOrderRepository.findById(voucherOrderDetail.getOrderId()).get();
        copyProperties(voucherOrder,orderResponse);

        Transaction transaction = transactionService.findTransactionByOrderId(voucherOrderDetail.getOrderId());
        orderResponse.setTransactionId(transaction.getId());

        Voucher voucher = voucherRepository.findById(voucherOrderDetail.getVoucherId()).get();
        VoucherResponse voucherResponse = voucherTransformer.convertEntityToResponse(voucher);
        orderResponse.setVoucher(voucherResponse);

        return orderResponse;
    }

    public List<OrderResponse> convertEntityListToResponse(List<VoucherOrderDetail> voucherOrderDetails){
        List<OrderResponse> orderResponses = new ArrayList<>();
        voucherOrderDetails.forEach((VoucherOrderDetail v)->{
            orderResponses.add(convertEntityToResponse(v));
        });
        return orderResponses;
    }
}
