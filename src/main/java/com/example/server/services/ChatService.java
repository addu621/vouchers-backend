package com.example.server.services;

import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Chat;
import com.example.server.entities.ChatMessage;
import com.example.server.entities.Person;
import com.example.server.repositories.ChatMessageRepository;
import com.example.server.repositories.ChatRepository;
import com.example.server.repositories.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private PersonService personService;

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
        Person person = personService.findById(senderId);
        if(person.getIsAdmin()){
            markChatSeenForAdmin(chatId);
            markChatUnSeenForUser(chatId);
        }else{
            markChatSeenForUser(chatId);
            markChatUnSeenForAdmin(chatId);
        }
        return chatMessageRepository.save(chatMessage);
    }

    public boolean isChatUnseenForUser(long issueId,long userId){
        Person person = personService.findById(userId);
        Chat chat = chatRepository.findById(issueId).get();
        if(person.getIsAdmin()){
            return chat.isSeenByAdmin();
        }
        return chat.isSeenByUser();
    }

    public boolean markChatSeenForUser(long chatId){
        Chat chat = chatRepository.findById(chatId).get();
        chat.setSeenByUser(true);
        chatRepository.save(chat);
        return true;
    }

    public boolean markChatUnSeenForUser(long chatId){
        Chat chat = chatRepository.findById(chatId).get();
        chat.setSeenByUser(false);
        chatRepository.save(chat);
        return true;
    }

    public boolean markChatUnSeenForAdmin(long chatId){
        Chat chat = chatRepository.findById(chatId).get();
        chat.setSeenByAdmin(false);
        chatRepository.save(chat);
        return true;
    }

    public boolean markChatSeenForAdmin(long chatId){
        Chat chat = chatRepository.findById(chatId).get();
        chat.setSeenByAdmin(true);
        chatRepository.save(chat);
        return true;
    }

    public Chat createChatForIssue(long issueId){
        Chat chat = new Chat();
        chat.setId(issueId);
        chat.setSeenByAdmin(true);
        chat.setSeenByUser(true);
        return chatRepository.save(chat);
    }
}
