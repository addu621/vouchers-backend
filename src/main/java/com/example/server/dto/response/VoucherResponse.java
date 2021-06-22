package com.example.server.dto.response;

import com.example.server.enums.VoucherVerificationStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class VoucherResponse {
    private Long id;
    private BigDecimal voucherValue;
    private String voucherCode;
    private BigDecimal sellingPrice;
    private String expiryDate;
    private boolean isNegotiable;
    private String description;
    private String title;
    private Long categoryId;
    private Long companyId;
    private String createdOn;
    private VoucherVerificationStatus verificationStatus;
    private String imageUrl;
}
