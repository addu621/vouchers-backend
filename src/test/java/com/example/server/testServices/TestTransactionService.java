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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        assertEquals(transaction.getId(),transactionRepository.save(transaction).getId());
    }

    @Test
    public void testFindTransactionsByUserId(){
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setId("xyz");
        transaction1.setUserId(1L);
        transaction1.setTransactionDate(new Date());

        Transaction transaction2 = new Transaction();
        transaction2.setId("abc");
        transaction2.setUserId(1L);
        transaction2.setTransactionDate(new Date());

        transactions.add(transaction1);
        transactions.add(transaction2);

        Mockito.when(transactionRepository.findByUserId(1)).thenReturn(transactions);
        assertEquals(2,transactionService.findTransactionsByUserId(1).size());
    }

    @Test
    public void testFindTransactionsByOrderId(){
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setId("xyz");
        transaction1.setOrderId(1L);
        transaction1.setTransactionDate(new Date());

        transactions.add(transaction1);

        Mockito.when(transactionRepository.findByOrderId(1)).thenReturn(transactions);
        assertEquals("xyz",transactionService.findTransactionByOrderId(1).getId());
    }
}
