package com.example.server.dto.response;

import lombok.Data;

@Data
public class PersonResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String imageUrl;
    private String mobile;
    private Long id;
    private String accountNumber;
    private String ifscCode;
    private String ssn;
    private boolean ssnVerified;
}
