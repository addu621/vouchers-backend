package com.example.server.dto.request;

import lombok.Data;

@Data
public class PersonRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String imgUrl;
    private String mobile;
}
