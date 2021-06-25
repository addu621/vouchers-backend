package com.example.server.entities;

import com.example.server.enums.NotificationType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "voucher_id")
    private Long voucherId;

    @Column(name = "buyer_id")
    private Long buyerId;

    @Column(name = "seller_id")
    private Long sellerId;

    @Column(name = "title")
    private String title;

    @Column(columnDefinition="TEXT")
    private String description;

    @CreationTimestamp
    private Date createdDate;

    @Column(name = "is_seen")
    private Boolean isSeen = false;

    @Column(name = "is_complete")
    private Boolean isComplete = false;

    @Column(name = "notification_type")
    private NotificationType notificationType;
}
