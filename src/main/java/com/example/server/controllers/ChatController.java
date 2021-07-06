package com.example.server.controllers;

import com.example.server.dto.request.ChatMessageRequest;
import com.example.server.dto.response.ChatResponse;
import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.transformer.ChatTransformer;
import com.example.server.entities.Chat;
import com.example.server.entities.Person;
import com.example.server.services.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatTransformer chatTransformer;

    @GetMapping("/issues/{issueId}/chat")
    public ChatResponse getChatByIssueId(HttpServletRequest request,@PathVariable long issueId){
        Chat chat = chatService.getChatById(issueId);
        Person personDetails = (Person) request.getAttribute("person");
        return chatTransformer.convertEntityToResponse(chat,personDetails);
    }

    @PostMapping("/newMessage")
    public GenericResponse addMessageToChat(HttpServletRequest request, @RequestBody ChatMessageRequest chatMessageRequest){
        Person personDetails = (Person) request.getAttribute("person");
        System.out.println(chatService);
        chatService.addChatMessage(chatMessageRequest.getIssueId(),personDetails.getId(),chatMessageRequest.getMessage());
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setStatus(201);
        genericResponse.setMessage("Message Added Succesfully!");
        return genericResponse;
    }

    @PostMapping("/chats/{chatId}/markSeen")
    public GenericResponse markChatSeen(HttpServletRequest request,@PathVariable long chatId){
        Person personDetails = (Person) request.getAttribute("person");
        if(personDetails.getIsAdmin()){
            chatService.markChatSeenForAdmin(chatId);
        }else{
            chatService.markChatSeenForUser(chatId);
        }
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setStatus(201);
        genericResponse.setMessage("Marked chat as seen for current user!");
        return genericResponse;
    }
}
