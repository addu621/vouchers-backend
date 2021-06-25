package com.example.server.services;

import com.example.server.entities.Transaction;
import com.example.server.enums.TransactionType;
import com.example.server.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Transaction addTransaction(long userId,TransactionType transactionType,BigDecimal amount){
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setTransactionDate(new Date());
        transaction.setTransactionType(transactionType);
        transaction.setTotalPrice(amount);
        return this.transactionRepository.save(transaction);
    }

    public List<Transaction> sortByTime(List<Transaction> transactions){
         transactions.sort((Transaction t1,Transaction t2)->t2.getTransactionDate().compareTo(t1.getTransactionDate()));
         return transactions;
    }

    public List<Transaction> findTransactionsByUserId(long userId){
        List<Transaction> transactions =  (List<Transaction>) this.transactionRepository.findByUserId(userId);
        return sortByTime(transactions);
    }
}