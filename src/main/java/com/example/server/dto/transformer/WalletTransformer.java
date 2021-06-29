package com.example.server.dto.transformer;

import com.example.server.dto.response.WalletResponse;
import com.example.server.entities.Transaction;
import com.example.server.entities.Wallet;
import com.example.server.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@AllArgsConstructor
public class WalletTransformer {

    private final TransactionService transactionService;
    private final TransactionTransformer transactionTransformer;

    public WalletResponse convertEntityToResponse(Wallet wallet){
        WalletResponse walletResponse = new WalletResponse();
        copyProperties(wallet,walletResponse);
        List<Transaction> transactions = transactionService.findTransactionsByUserId(wallet.getId());
        walletResponse.setTransactions(transactionTransformer.convertEntityListToResponseList(transactions));
        return walletResponse;
    }
}
