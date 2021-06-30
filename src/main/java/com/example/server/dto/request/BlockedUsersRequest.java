package com.example.server.dto.request;

import lombok.Data;

import javax.persistence.Column;

@Data
public class BlockedUsersRequest {
    private Long id;
    private String email;
    private String description;
}
