package com.example.server.dto.request;

import lombok.Data;

import javax.persistence.Column;

@Data
public class PersonRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String imageUrl;
    private String mobile;
    private String accountNumber;
    private String ifscCode;
    private String ssn;
    private boolean ssnVerified;
}
