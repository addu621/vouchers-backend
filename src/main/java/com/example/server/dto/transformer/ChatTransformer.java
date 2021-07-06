package com.example.server.dto.transformer;

import com.example.server.dto.response.ChatMessageResponse;
import com.example.server.dto.response.ChatResponse;
import com.example.server.entities.Chat;
import com.example.server.entities.ChatMessage;
import com.example.server.entities.Person;
import com.example.server.services.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@AllArgsConstructor
public class ChatTransformer {

    private ChatService chatService;
    private ChatMessageTransformer chatMessageTransformer;

    public ChatResponse convertEntityToResponse(Chat chat, Person person){
        ChatResponse chatResponse = new ChatResponse();
        copyProperties(chat,chatResponse);
        List<ChatMessage> chatMessageList = chatService.getAllChatMessagesByChatId(chat.getId());
        List<ChatMessageResponse> chatMessageResponses = chatMessageTransformer.convertEntityListToResponseList(chatMessageList);
        chatResponse.setMessages(chatMessageResponses);
        boolean isSeen = false;
        if(person.getIsAdmin()){
            isSeen = chat.isSeenByAdmin();
        }else{
            isSeen = chat.isSeenByUser();
        }
        chatResponse.setSeen(isSeen);
        return chatResponse;
    }
}
