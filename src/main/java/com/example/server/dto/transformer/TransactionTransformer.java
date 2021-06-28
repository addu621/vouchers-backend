package com.example.server.dto.transformer;

import com.example.server.dto.response.TransactionResponse;
import com.example.server.entities.Transaction;
import com.example.server.entities.VoucherOrder;
import com.example.server.services.TransactionService;
import com.example.server.services.VoucherOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@AllArgsConstructor
public class TransactionTransformer {
    private final TransactionService transactionService;
    private final VoucherOrderService voucherOrderService;

    public TransactionResponse convertEntityToResponse(Transaction transaction){
        TransactionResponse transactionResponse = new TransactionResponse();
        copyProperties(transaction,transactionResponse);
        VoucherOrder voucherOrder = this.voucherOrderService.findByTransactionId(transaction.getId());
        transactionResponse.setOrderId(voucherOrder.getId());
        return transactionResponse;
    }

    public List<TransactionResponse> convertEntityListToResponseList(List<Transaction> transactions){
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        transactions.forEach((Transaction transaction)->{
            transactionResponses.add(convertEntityToResponse(transaction));
        });
        return transactionResponses;
    }
}
