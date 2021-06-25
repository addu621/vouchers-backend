package com.example.server.dto.request;

import lombok.Data;

import java.util.List;
@Data
public class FilterRequest {
    private List<Long>companies;
    private List<Long> categories;
}
