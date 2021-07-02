package com.example.server.dto.response;

import com.example.server.enums.TransactionStatus;
import com.example.server.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionResponse {
    private String id;
    private long orderId;
    private BigDecimal totalPrice;
    private Date transactionDate;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private int coinsAddedToWallet;
    private int coinsDeductedFromWallet;
}
