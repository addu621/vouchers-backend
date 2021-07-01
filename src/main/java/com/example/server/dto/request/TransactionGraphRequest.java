package com.example.server.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class TransactionGraphRequest {
    private String startDate;
    private String endDate;
}
