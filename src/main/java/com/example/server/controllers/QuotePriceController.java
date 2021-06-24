package com.example.server.controllers;

import com.example.server.dto.request.DealRequest;
import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Person;
import com.example.server.entities.VoucherDeal;
import com.example.server.services.VoucherDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class QuotePriceController {

    @Autowired
    VoucherDealService voucherDealService;

    @PostMapping("/quote")
    public GenericResponse quotePrice(HttpServletRequest request, @RequestBody DealRequest dealRequest){
        GenericResponse genericResponse = new GenericResponse();
        Person personDetails = (Person) request.getAttribute("person");
        Long buyerId = personDetails.getId();

        GenericResponse result = voucherDealService.quotePrice(buyerId,dealRequest.getVoucherId(),dealRequest.getQuotedPrice());

        return result;

    }
}

