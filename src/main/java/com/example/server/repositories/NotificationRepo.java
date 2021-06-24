package com.example.server.repositories;

import com.example.server.entities.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notifications,String> {

    public List<Notifications> findAllByReceiverId(Long receiverId);

    public Notifications findByNotificationId(Long notificationId);
}
