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
    public ChatResponse getChatByIssueId(@PathVariable long issueId){
        Chat chat = chatService.getChatById(issueId);
        return chatTransformer.convertEntityToResponse(chat);
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
}
