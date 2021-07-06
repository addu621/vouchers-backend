package com.example.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @Column(name = "chat_id")
    private long id;

    @Column(name = "last_updated")
    private Date lastUpdated;
}
