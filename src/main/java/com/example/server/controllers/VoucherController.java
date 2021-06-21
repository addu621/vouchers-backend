package com.example.server.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.server.entities.Person;
import com.example.server.entities.Voucher;
import com.example.server.repositories.VoucherRepo;
import com.example.server.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class VoucherController {
    @PostMapping("/search-voucher")
    public List<Voucher> searchVoucher(@RequestBody Map<String, String> req) {
        String searchInput = req.get("input");
        List<Voucher> searchResult =  voucherService.searchVoucher(searchInput);
        return searchResult;
    }

    @Autowired
    private VoucherService voucherService;
    @PostMapping("/test")
    public String signUp(@RequestBody Map<String, String> input){
        return input.get("email");
    }
}
