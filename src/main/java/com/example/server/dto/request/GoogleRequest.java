package com.example.server.dto.request;

import lombok.Data;

@Data
public class GoogleRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String photoUrl;
}
