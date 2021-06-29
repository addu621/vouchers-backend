package com.example.server.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckoutPageCost {
    private BigDecimal itemsValue;
    private BigDecimal taxCalculated;
    private Integer  loyaltyCoins;
    private BigDecimal finalCost;

}
