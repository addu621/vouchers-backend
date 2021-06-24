package com.example.server.services;

import com.example.server.entities.Notifications;
import com.example.server.repositories.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    public List<Notifications> getNotifications(Long receiverId){
        List<Notifications> notificationsList = notificationRepo.findAllByReceiverId(receiverId);
        return notificationsList;
    }

    public void notificationSeen(Long notificationId){
        Notifications notification = notificationRepo.findByNotificationId(notificationId);
        notification.setIsSeen(true);
        notificationRepo.save(notification);
    }
}
