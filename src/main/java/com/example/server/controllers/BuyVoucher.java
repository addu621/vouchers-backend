package com.example.server.controllers;

import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Person;
import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherDeal;
import com.example.server.repositories.VoucherDealRepository;
import com.example.server.services.VoucherDealService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BuyVoucher {
    @Autowired
    VoucherDealService voucherDealService;

    @PostMapping("/buy/voucher")
    public GenericResponse buyVoucher(HttpServletRequest request,Long voucherId){
        Person personDetails = (Person) request.getAttribute("person");
        Long buyerId = personDetails.getId();

        VoucherDeal voucherDeal = voucherDealService.buyVoucher(buyerId,voucherId);
        GenericResponse genericResponse= new GenericResponse();

        if(voucherDeal!=null){
            genericResponse.setMessage("Voucher Bought Successfully");
            genericResponse.setStatus(200);
        }
        return genericResponse;
    }
}
