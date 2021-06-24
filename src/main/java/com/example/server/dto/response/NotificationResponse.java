package com.example.server.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationResponse {
    private Integer voucherId;
    private Integer receiverId;
    private String title;
    private String description;
    private Date createdOn;
    private Boolean isSeen;
    private String brandImageUrl;
}
