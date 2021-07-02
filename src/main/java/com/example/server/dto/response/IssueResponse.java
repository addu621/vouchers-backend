package com.example.server.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class IssueResponse {
    private Long issueId;
    private Long orderItemId;
    private String comment;
    private Long reporterId;
    private Boolean isRead = false;
    private Boolean isClosed = false;
    private Date createdDate;
    private OrderResponse order;
}
