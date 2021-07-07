package com.example.server.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderResponse {

    public Long orderId;
    public Long orderItemId;
    public Date orderDate;
    public String transactionId;
    private BigDecimal orderItemPrice;
    public VoucherResponse voucher;
    public long issueId;
    public Boolean isChatUnSeen;
}
