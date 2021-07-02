package com.example.server.dto.request;

import lombok.Data;

@Data
public class PaymentOrderRequest {
    private Boolean hasRedeemed=false;
}
