package com.example.server.dto.transformer;

import com.example.server.dto.response.OrderResponse;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.*;
import com.example.server.repositories.IssueRepo;
import com.example.server.repositories.VoucherOrderRepository;
import com.example.server.repositories.VoucherRepository;
import com.example.server.services.ChatService;
import com.example.server.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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
    private final IssueRepo issueRepo;
    private final ChatService chatService;

    @SneakyThrows
    public OrderResponse convertEntityToResponse(VoucherOrderDetail voucherOrderDetail,Person person){
        OrderResponse orderResponse = new OrderResponse();
        copyProperties(voucherOrderDetail,orderResponse);
        orderResponse.setOrderItemPrice(voucherOrderDetail.getItemPrice());
        orderResponse.setOrderItemId(voucherOrderDetail.getId());

        Issue issue = issueRepo.findByOrderItemId(orderResponse.getOrderItemId());
        if(issue!=null){
            orderResponse.setIssue(issue);
            boolean isSeen = chatService.isChatUnseenForUser(issue.getIssueId(),person.getId());
            orderResponse.setIsChatUnSeen(isSeen);
        }

        VoucherOrder voucherOrder = voucherOrderRepository.findById(voucherOrderDetail.getOrderId()).get();
        copyProperties(voucherOrder,orderResponse);

        Transaction transaction = transactionService.findTransactionByOrderId(voucherOrderDetail.getOrderId());
        orderResponse.setTransactionId(transaction.getId());

        Voucher voucher = voucherRepository.findById(voucherOrderDetail.getVoucherId()).get();
        VoucherResponse voucherResponse = voucherTransformer.convertEntityToResponse(voucher);
        orderResponse.setVoucher(voucherResponse);

        return orderResponse;
    }

    @SneakyThrows
    public OrderResponse convertEntityToResponse(VoucherOrderDetail voucherOrderDetail){
        OrderResponse orderResponse = new OrderResponse();
        copyProperties(voucherOrderDetail,orderResponse);
        orderResponse.setOrderItemPrice(voucherOrderDetail.getItemPrice());
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

    public List<OrderResponse> convertEntityListToResponse(List<VoucherOrderDetail> voucherOrderDetails, Person person){
        List<OrderResponse> orderResponses = new ArrayList<>();
        voucherOrderDetails.forEach((VoucherOrderDetail v)->{
            orderResponses.add(convertEntityToResponse(v,person));
        });
        return orderResponses;
    }

    public List<OrderResponse> convertEntityListToResponse(List<VoucherOrderDetail> voucherOrderDetails){
        List<OrderResponse> orderResponses = new ArrayList<>();
        voucherOrderDetails.forEach((VoucherOrderDetail v)->{
            orderResponses.add(convertEntityToResponse(v));
        });
        return orderResponses;
    }
}
