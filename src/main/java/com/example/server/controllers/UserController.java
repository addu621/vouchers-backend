package com.example.server.controllers;

import com.example.server.dto.request.JwtTokenRequest;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.*;
import com.example.server.model.JwtUtil;
import com.example.server.services.UserService;
import com.example.server.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


}
