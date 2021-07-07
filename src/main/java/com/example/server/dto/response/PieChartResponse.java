package com.example.server.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PieChartResponse {
    private BigDecimal buyerAmountSum;
    private BigDecimal sellerAmountSum;
    private BigDecimal commissionSum;

    public PieChartResponse(BigDecimal buyerAmountSum, BigDecimal sellerAmountSum, BigDecimal commissionSum) {
        this.buyerAmountSum = buyerAmountSum;
        this.sellerAmountSum = sellerAmountSum;
        this.commissionSum = commissionSum;
    }
}
