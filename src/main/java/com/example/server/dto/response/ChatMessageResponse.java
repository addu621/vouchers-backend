package com.example.server.dto.response;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class ChatMessageResponse {
    private long id;
    private long senderId;
    private long chatId;
    private String message;
    private Date sentOn;
}
