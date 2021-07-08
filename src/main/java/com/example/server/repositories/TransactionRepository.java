package com.example.server.repositories;

import com.example.server.dto.response.PieChartResponse;
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
    List<Transaction> findByIdAndOrderId(String transactionId,long orderId);

//    @Query("Select new com.example.server.dto.response.TransactionGraphResponse(TO_CHAR(t.transactionDate,'YYYY-MM-DD'), Count(t.transactionDate)) "+
//            "from Transaction as t where TO_CHAR(t.transactionDate,'YYYY-MM-DD') >= :startDate and  TO_CHAR(t.transactionDate,'YYYY-MM-DD') <= :endDate group by TO_CHAR(t.transactionDate,'YYYY-MM-DD')")
//    List<?> generateGraph(@Param("startDate") String startDate, @Param("endDate") String endDate);
//
//    @Query("Select new com.example.server.dto.response.TransactionGraphResponse(TO_CHAR(t.transactionDate, 'yyyy-mm') AS  date_to_month , Count(t.id)) from Transaction as t where TO_CHAR(t.transactionDate,'yyyy-mm') >= :startDate and  TO_CHAR(t.transactionDate,'yyyy-mm') <= :endDate GROUP BY TO_CHAR(t.transactionDate, 'yyyy-mm') ORDER BY date_to_month ASC")
//    List<?> generateGraphByMonth(@Param("startDate") String startDate, @Param("endDate") String endDate);
//
//    @Query("Select new com.example.server.dto.response.TransactionGraphResponse(TO_CHAR(t.transactionDate, 'yyyy') AS  date_to_year , Count(t.id))  from Transaction as t where TO_CHAR(t.transactionDate,'yyyy') >= :startDate and  TO_CHAR(t.transactionDate,'yyyy') <= :endDate GROUP BY TO_CHAR(t.transactionDate, 'yyyy') ORDER BY date_to_year ASC")
//    List<?> generateGraphByYears(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
