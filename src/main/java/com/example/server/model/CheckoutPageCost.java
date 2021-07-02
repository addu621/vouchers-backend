package com.example.server.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckoutPageCost {
    private BigDecimal itemsValue;
    private BigDecimal taxCalculated;

    private Integer  loyaltyCoinsInWallet;
    private Integer  loyaltyCoinsEarned;
    private Integer existingLoyaltyCoinsValue;
    private Integer coinBalanceAfterRedemption;

    private BigDecimal finalCost;
    private BigDecimal finalCostAfterCoinRedeem;


}
