package com.example.server.services;

import com.example.server.entities.Notifications;
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

    public List<Notifications> getNotifications(Long receiverId){
        List<Notifications> notificationsList = notificationRepo.findAllByReceiverId(receiverId);
        return notificationsList;
    }

    public void notificationSeen(Long notificationId){
        Notifications notification = notificationRepo.findByNotificationId(notificationId);
        notification.setIsSeen(true);
        notificationRepo.save(notification);
    }

    public String postNotification(Long receiverId,Long voucherId,String notificationType){
        Voucher voucher = voucherRepository.findById(voucherId).get();
        if(voucher==null)
        {
            return "Voucher not found!!!";
        }
        Person person = personRepo.findById(receiverId).get();
        if(person==null)
        {
            return "User not found!!!";
        }
        Notifications newNotifcation = new Notifications();
        newNotifcation.setReceiverId(receiverId);
        newNotifcation.setVoucherId(voucherId);
        newNotifcation.setIsSeen(false);
        newNotifcation.setBrandImageUrl(voucher.getImageUrl());
        if(notificationType.equals("bargain request"))
        {
            //for buyer
            newNotifcation.setTitle("Price Quoted");
            newNotifcation.setDescription("Username abc has quoted xyz price");
        }
        else if(notificationType.equals("purchase complete")) {
            //to both buyer and seller
        }
        else if(notificationType.equals("admin verified")) {
            //to seller  (admin verifies)
        }
        return "Notification saved!!!";
    }
}
