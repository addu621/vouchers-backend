package com.example.server.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PieChartResponse {
    private BigDecimal totalTransactionSum;
    private long coinsValueEarnedByBuyer;
    private long coinsValueRedeemedByBuyer;
    private Double commission;

    public PieChartResponse(BigDecimal totalTransactionSum, long coinsValueEarnedByBuyer, long coinsValueRedeemedByBuyer, Double commission) {
        this.totalTransactionSum = totalTransactionSum;
        this.coinsValueEarnedByBuyer = coinsValueEarnedByBuyer;
        this.coinsValueRedeemedByBuyer = coinsValueRedeemedByBuyer;
        this.commission = commission;
    }
}
