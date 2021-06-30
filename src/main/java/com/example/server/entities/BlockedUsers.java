package com.example.server.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "blocked_users")
public class BlockedUsers {

    @Column(name = "person_id")
    private Long id;

    @Column
    private String email;

    @Column
    private String description;
}
