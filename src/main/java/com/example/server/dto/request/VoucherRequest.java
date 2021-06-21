package com.example.server.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VoucherRequest {
    private BigDecimal voucherValue;
    private String voucherCode;
    private BigDecimal sellingPrice;
    private String expiryDate;
    private boolean isNegotiable;
    private String description;
    private String title;
    private Long categoryId;
    private Long companyId;
}
