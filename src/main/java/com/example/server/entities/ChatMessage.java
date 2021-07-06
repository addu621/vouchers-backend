package com.example.server.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "chat_messages")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private long id;

    @Column(name = "sender_id")
    private long senderId;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "message")
    private String message;

    @Column(name = "sent_on")
    private Date sentOn;
}
