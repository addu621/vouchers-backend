package com.example.server.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "receiver_id")
    private Long receiverId;

    private String title;

    private String description;

    @CreationTimestamp
    private Date createdOn;

    @Column(name = "is_seen")
    private Boolean isSeen;

    @Column(name = "brand_image_url")
    private String brandImageUrl;
}
