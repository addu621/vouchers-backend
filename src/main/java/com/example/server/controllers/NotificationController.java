package com.example.server.controllers;

import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Notifications;
import com.example.server.entities.Person;
import com.example.server.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getNotifications")
    public List<Notifications> getNotifications(HttpServletRequest request) {
        Person personDetails = (Person) request.getAttribute("person");
        List<Notifications> result = notificationService.getNotifications(personDetails.getId());
        return result;
    }

    @GetMapping("/notificationSeen")
    public void notificationSeen(Map<String,Long> mp) {
        notificationService.notificationSeen(mp.get("notificationId"));
    }
}
