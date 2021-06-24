package com.example.server.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

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
    private Long sellerId;
    private String imageUrl;
}
