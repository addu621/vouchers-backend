package com.example.server.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "notifications")
public class Notifications {

    @Column(name = "voucher_id")
    private Integer voucherId;

    @Column(name = "receiver_id")
    private Integer receiverId;

    private String title;

    private String description;

    @CreationTimestamp
    private Date createdOn;

    @Column(name = "is_seen")
    private Boolean isSeen;

    @Column(name = "brand_image_url")
    private String brandImageUrl;
}
