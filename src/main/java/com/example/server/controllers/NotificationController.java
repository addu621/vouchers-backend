package com.example.server.controllers;

import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Notification;
import com.example.server.entities.Person;
import com.example.server.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getNotifications")
    public List<Notification> getNotifications(HttpServletRequest request) {
        Person personDetails = (Person) request.getAttribute("person");
        List<Notification> result = notificationService.getNotifications(personDetails.getId());
        return result;
    }

    @PutMapping("/notification/{notificationId}/is-seen")
    public GenericResponse notificationSeen(@PathVariable Long notificationId,@RequestBody Map<String,Boolean> mp) {
        notificationService.notificationSeen(mp.get("seen"),notificationId);
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage("Updated successfully");
        genericResponse.setStatus(200);
        return genericResponse;
    }

    @PutMapping("/notification/{notificationId}/is-complete")
    public GenericResponse notificationCompleted(@PathVariable Long notificationId,@RequestBody Map<String,Boolean> mp) {
        notificationService.notificationCompleted(mp.get("completed"),notificationId);
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage("Updated successfully");
        genericResponse.setStatus(200);
        return genericResponse;
    }
}
