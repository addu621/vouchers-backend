package com.example.server.dto.response;

import lombok.Data;

@Data
public class PersonResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String imgUrl;
    private String mobile;
    private Long id;
}
