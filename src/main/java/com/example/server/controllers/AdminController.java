package com.example.server.controllers;

import com.example.server.dto.request.BlockedUsersRequest;
import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Person;
import com.example.server.repositories.BlockedUsersRepository;
import com.example.server.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private BlockedUsersRepository blockedUsersRepository;

    @Autowired
    private AdminService adminService;

    @PostMapping("/block-user")
    public GenericResponse blockUser(@RequestBody BlockedUsersRequest person) {
        return adminService.blockUser(person);
    }
}
