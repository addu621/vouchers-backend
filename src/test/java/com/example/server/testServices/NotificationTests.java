package com.example.server.testServices;

import com.example.server.entities.Notification;
import com.example.server.enums.NotificationType;
import com.example.server.repositories.NotificationRepo;
import com.example.server.services.NotificationService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class NotificationTests {

    @MockBean
    private NotificationRepo notificationRepo;

    @Autowired
    private NotificationService notificationService;

    @Test
    public void getNotificationsTest(){

        Notification notification1 = new Notification();
        notification1.setTitle("Test Voucher1");
        notification1.setDescription("Voucher Approved");
        notification1.setNotificationType(NotificationType.VOUCHER_APPROVED);
        notification1.setReceiverId(1L);

        Notification notification2 = new Notification();
        notification2.setTitle("Test Voucher2");
        notification2.setDescription("Voucher Rejected");
        notification2.setNotificationType(NotificationType.VOUCHER_REJECTED);
        notification1.setReceiverId(1L);


        Notification notification3 = new Notification();
        notification3.setTitle("Test Voucher3");
        notification3.setDescription("Voucher Price Quoted");
        notification3.setNotificationType(NotificationType.NEW_PRICE_QUOTED);
        notification1.setReceiverId(1L);

        List<Notification> notificationList= new ArrayList<>();
        notificationList.add(notification1);
        notificationList.add(notification2);
        notificationList.add(notification3);

        when(notificationRepo.findAllByReceiverIdOrderByCreatedDateDesc(Mockito.anyLong())).thenReturn(notificationList);

        assertEquals(3,notificationService.getNotifications(1L).size());
    }

    @Test
    public void notificationSeenTest(){
        Notification notification = new Notification();

        notification.setTitle("Test Voucher1");
        notification.setDescription("Voucher Approved");
        notification.setNotificationType(NotificationType.VOUCHER_APPROVED);
        notification.setReceiverId(1L);
        notification.setIsSeen(true);

        when(notificationRepo.save(notification)).thenReturn(notification);

        assertEquals(notification,notificationService.createNewNotification(notification));
    }

    @Test
    public void notificationCompletedTest(){
        Notification notification = new Notification();

        notification.setTitle("Test Voucher1");
        notification.setDescription("Voucher Approved");
        notification.setNotificationType(NotificationType.VOUCHER_APPROVED);
        notification.setReceiverId(1L);
        notification.setIsComplete(true);

        when(notificationRepo.save(notification)).thenReturn(notification);

        assertEquals(notification,notificationService.createNewNotification(notification));
    }

    @Test
    public void createNewNotification(){
        Notification notification = new Notification();

        notification.setTitle("Test Voucher1");
        notification.setDescription("Voucher Approved");
        notification.setNotificationType(NotificationType.VOUCHER_APPROVED);
        notification.setReceiverId(1L);
        notification.setIsComplete(true);

        when(notificationRepo.save(notification)).thenReturn(notification);

        assertEquals(notification,notificationService.createNewNotification(notification));

    }
}
