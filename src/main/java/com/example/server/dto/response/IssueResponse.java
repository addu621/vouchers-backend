package com.example.server.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class IssueResponse {
    private Long issueId;
    private Long orderItemId;
    private String comment;
    private Long userId;
    private Boolean isRead = false;
    private Boolean isClosed = false;
    private Date createdDate;
    private Boolean isChatUnseen;
    private OrderResponse order;
}
