package com.example.server.dto.response;

import com.example.server.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionResponse {
    private long id;
    private long orderId;
    private BigDecimal totalPrice;
    private Date transactionDate;
    private TransactionType transactionType;
}
