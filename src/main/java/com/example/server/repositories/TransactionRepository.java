package com.example.server.repositories;

import com.example.server.entities.Transaction;
import com.example.server.entities.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,String> {
    List<Transaction> findByUserId(long userId);
    List<Transaction> findByOrderId(long orderId);
}
