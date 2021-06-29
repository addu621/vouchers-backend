package com.example.server.dto.response;

import com.example.server.entities.Person;
import com.example.server.entities.VoucherCompany;
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
    private Date expiryDate;
    private boolean isNegotiable;
    private String description;
    private String title;
    private Long categoryId;
    private Long companyId;
    private String companyImgUrl;
    private String companyName;
    private Date createdOn;
    private VoucherVerificationStatus verificationStatus;
    private String imageUrl;
    private PersonResponse seller;
}
