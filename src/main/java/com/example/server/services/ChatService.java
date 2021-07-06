package com.example.server.services;

import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Chat;
import com.example.server.entities.ChatMessage;
import com.example.server.repositories.ChatMessageRepository;
import com.example.server.repositories.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getAllChatMessagesByChatId(long chatId){
        return (List<ChatMessage>) chatMessageRepository.findByChatId(chatId);
    }

    public Chat getChatById(long id){
        return chatRepository.findById(id).get();
    }

    public Chat setLastUpdated(long chatId){
        Chat chat = getChatById(chatId);
        chat.setLastUpdated(new Date());
        return chatRepository.save(chat);
    }

    public boolean isChatExist(long chatId){
        return chatRepository.findById(chatId).isPresent();
    }

    public ChatMessage addChatMessage(long chatId,long senderId,String message){
        if(!isChatExist(chatId)) createChatForIssue(chatId);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(message);
        chatMessage.setSentOn(new Date());
        chatMessage.setSenderId(senderId);
        chatMessage.setChatId(chatId);
        setLastUpdated(chatId);
        return chatMessageRepository.save(chatMessage);
    }

    public Chat createChatForIssue(long issueId){
        Chat chat = new Chat();
        chat.setId(issueId);
        return chatRepository.save(chat);
    }
}
