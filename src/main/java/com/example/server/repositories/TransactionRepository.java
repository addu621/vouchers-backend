package com.example.server.repositories;

import com.example.server.dto.response.TransactionGraphResponse;
import com.example.server.entities.Transaction;
import com.example.server.entities.Wallet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,String> {
    List<Transaction> findByUserId(long userId);
    List<Transaction> findByOrderId(long orderId);

    @Query("Select new com.example.server.dto.response.TransactionGraphResponse(t.transactionDate, Count(t.transactionDate)) "+
            "from Transaction as t where t.transactionDate between :startDate and :endDate group by t.transactionDate")
    List<?> generateGraph(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
