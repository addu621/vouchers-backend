package com.example.server.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DealRequest {
    private Long voucherId;
    private BigDecimal quotedPrice;
}
