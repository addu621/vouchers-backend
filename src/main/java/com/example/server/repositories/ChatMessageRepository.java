package com.example.server.repositories;

import com.example.server.entities.ChatMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage,Long> {
    List<ChatMessage> findByChatId(long chatId);
}
