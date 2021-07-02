package com.example.server.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionGraphResponse {
    private String date;
    private Long transactionCount;

    public TransactionGraphResponse(String date, Long transactionCount) {
        this.date = date;
        this.transactionCount = transactionCount;
    }
}
