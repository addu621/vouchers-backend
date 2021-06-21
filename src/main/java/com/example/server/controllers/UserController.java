package com.example.server.controllers;

import com.example.server.entities.VoucherCategory;
import com.example.server.entities.VoucherCompany;
import com.example.server.entities.VoucherType;
import com.example.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getVoucherCategories")
    public List<VoucherCategory> getVoucherCategories() {
        return userService.getAllVoucherCategory();
    }

    @GetMapping("/getVoucherCompanies")
    public List<VoucherCompany> getVoucherCompanies() {
        return userService.getAllVoucherCompany();
    }

    @GetMapping("/getVoucherTypes")
    public List<VoucherType> getVoucherTypes() {
        return userService.getAllVoucherType();
    }

    @PostMapping("/addCompany")
    public String addCompany(@RequestBody String company) {
        return userService.addCompany(company);
    }
}
