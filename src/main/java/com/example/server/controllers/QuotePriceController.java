package com.example.server.controllers;

import com.example.server.dto.request.DealRequest;
import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Person;
import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherDeal;
import com.example.server.services.VoucherDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class QuotePriceController {

    @Autowired
    VoucherDealService voucherDealService;

    @PostMapping("/quote")
    public GenericResponse quotePrice(HttpServletRequest request, @RequestBody DealRequest dealRequest){
        GenericResponse genericResponse = new GenericResponse();
        Person personDetails = (Person) request.getAttribute("person");
        Long buyerId = personDetails.getId();
        GenericResponse response = voucherDealService.quotePrice(buyerId,dealRequest.getVoucherId(),dealRequest.getQuotedPrice());

        return response;

    }

    @PostMapping("/quote/accept")
    public GenericResponse acceptQuotedPrice(HttpServletRequest request, @RequestBody DealRequest dealRequest){
        GenericResponse genericResponse = new GenericResponse();
//        Person personDetails = (Person) request.getAttribute("person");
//        Long sellerId = personDetails.getId();
//        List<VoucherDeal> list= (List<VoucherDeal>)
//                voucherDealService.acceptQuotedPrice(dealRequest.getVoucherId(),dealRequest.getBuyerId());
//
        return voucherDealService.acceptQuotedPrice(dealRequest.getVoucherId(), dealRequest.getBuyerId());
//
    }

}

