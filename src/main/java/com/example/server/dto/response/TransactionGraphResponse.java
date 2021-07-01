package com.example.server.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionGraphResponse {
    private Date date;
    private Long transactionCount;

    public TransactionGraphResponse(Date date, Long transactionCount) {
        this.date = date;
        this.transactionCount = transactionCount;
    }
}
