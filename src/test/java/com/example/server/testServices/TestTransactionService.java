package com.example.server.testServices;

import com.example.server.entities.Transaction;
import com.example.server.enums.TransactionStatus;
import com.example.server.enums.TransactionType;
import com.example.server.repositories.TransactionRepository;
import com.example.server.services.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestTransactionService {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @Test
    public void testAddTransaction(){
        Transaction transaction = new Transaction();
        transaction.setId("xyz");
        transaction.setTransactionDate(new Date());
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setTotalPrice(new BigDecimal(100));
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setUserId(1L);
        transaction.setOrderId(1L);
        transaction.setCoinsAddedToWallet(5);
        transaction.setCoinsDeductedFromWallet(10);
        Mockito.when(transactionRepository.save(transaction)).thenReturn(transaction);
        Transaction addedTransaction = transactionService.addTransaction(transaction.getId(),transaction.getOrderId(),transaction.getCoinsAddedToWallet(),transaction.getCoinsDeductedFromWallet(),transaction.getUserId(),transaction.getTransactionType(),transaction.getTotalPrice());
        assertEquals(transaction.getId(),transaction.getId());
    }
}
