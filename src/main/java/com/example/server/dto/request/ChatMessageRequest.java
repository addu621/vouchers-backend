package com.example.server.dto.request;

import lombok.Data;

@Data
public class ChatMessageRequest {
    private String message;
    private long issueId;
}
