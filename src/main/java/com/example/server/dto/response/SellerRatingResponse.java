package com.example.server.dto.response;

import lombok.Data;

@Data
public class SellerRatingResponse {
    private Long sellerId;
    private Double sellerRating;

    public SellerRatingResponse(Long sellerId, Double sellerRating) {
        this.sellerId = sellerId;
        this.sellerRating = sellerRating;
    }
}
