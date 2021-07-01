package com.example.server.dto.request;

import lombok.Data;

@Data
public class SellerRatingRequest {
    private Long sellerId;
    private Long voucherId;
    private int stars;
    private String comment;
}
