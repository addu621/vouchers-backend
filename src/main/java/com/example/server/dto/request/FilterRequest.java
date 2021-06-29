package com.example.server.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class FilterRequest {
    private List<Long>companies;
    private List<Long> categories;
    private Double averageRating;
}
