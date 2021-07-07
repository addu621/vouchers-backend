package com.example.server.repositories;

import com.example.server.dto.response.PieChartResponse;
import com.example.server.entities.AmountTransfer;
import com.example.server.enums.AmountTransferStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmountTransferRepository extends CrudRepository<AmountTransfer,Long> {
    List<AmountTransfer> findByAmountTransferStatus(AmountTransferStatus amountTransferStatus);

    @Query("Select new com.example.server.dto.response.PieChartResponse(SUM(t.buyerAmount),SUM(t.sellerAmount),SUM(t.commissionEarned))  from AmountTransfer as t where TO_CHAR(t.createdDate,'yyyy-mm-dd') >= :startDate and  TO_CHAR(t.createdDate,'yyyy-mm-dd') <= :endDate")
    PieChartResponse getRevenueByDay(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("Select new com.example.server.dto.response.PieChartResponse(SUM(t.buyerAmount),SUM(t.sellerAmount),SUM(t.commissionEarned))  from AmountTransfer as t where TO_CHAR(t.createdDate,'yyyy-mm') >= :startDate and  TO_CHAR(t.createdDate,'yyyy-mm') <= :endDate")
    PieChartResponse getRevenueByMonth(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("Select new com.example.server.dto.response.PieChartResponse(SUM(t.buyerAmount),SUM(t.sellerAmount),SUM(t.commissionEarned))  from AmountTransfer as t where TO_CHAR(t.createdDate,'yyyy') >= :startDate and  TO_CHAR(t.createdDate,'yyyy') <= :endDate")
    PieChartResponse getRevenueByYear(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
