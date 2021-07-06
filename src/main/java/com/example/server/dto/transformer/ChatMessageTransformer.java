package com.example.server.dto.transformer;

import com.example.server.dto.response.ChatMessageResponse;
import com.example.server.entities.ChatMessage;
import com.example.server.services.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@AllArgsConstructor
public class ChatMessageTransformer {
    private final ChatService chatService;

    public ChatMessageResponse convertEntityToResponse(ChatMessage chatMessage){
        ChatMessageResponse chatMessageResponse = new ChatMessageResponse();
        copyProperties(chatMessage,chatMessageResponse);
        return chatMessageResponse;
    }

    public List<ChatMessageResponse> convertEntityListToResponseList(List<ChatMessage> chatMessageList){
        List<ChatMessageResponse> chatMessagesResponse = new ArrayList<>();
        for(ChatMessage chatMessage:chatMessageList){
            chatMessagesResponse.add(convertEntityToResponse(chatMessage));
        }
        return chatMessagesResponse;
    }
}
