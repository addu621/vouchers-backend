package com.example.server.services;

import com.example.server.entities.Notification;
import com.example.server.entities.Person;
import com.example.server.entities.Voucher;
import com.example.server.repositories.NotificationRepo;
import com.example.server.repositories.PersonRepo;
import com.example.server.repositories.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private PersonRepo personRepo;

    public List<Notification> getNotifications(Long receiverId){
        List<Notification> notificationsList = notificationRepo.findAllByReceiverIdOrderByCreatedDateDesc(receiverId);
        return notificationsList;
    }

    public void notificationSeen(boolean isSeen,Long notificationId){
        Notification notification = notificationRepo.findById(notificationId).get();
        notification.setIsSeen(isSeen);
        notificationRepo.save(notification);
    }

    public void notificationCompleted(boolean isCompleted,Long notificationId){
        Notification notification = notificationRepo.findById(notificationId).get();
        notification.setIsComplete(isCompleted);
        notificationRepo.save(notification);
    }

    public Notification createNewNotification(Notification notification){
        Notification createdNotification = notificationRepo.save(notification);
        return createdNotification;
    }
}
