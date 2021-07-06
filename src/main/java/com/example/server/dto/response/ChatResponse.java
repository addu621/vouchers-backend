package com.example.server.dto.response;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
public class ChatResponse {

    private long id;
    private List<ChatMessageResponse> messages;
}
