package com.example.server.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderResponse {

    public Long orderId;
    public Long voucherId;
    public Date orderDate;
    public String transactionId;
    private BigDecimal orderPrice;
    public VoucherResponse voucherResponse;
}
