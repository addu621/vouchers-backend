package com.example.server.controllers;

import com.example.server.dto.response.OrderResponse;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.dto.transformer.OrderTransformer;
import com.example.server.entities.Person;
import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherOrderDetail;
import com.example.server.services.VoucherOrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final VoucherOrderService voucherOrderService;
    private final OrderTransformer orderTransformer;

    @GetMapping("/users/buyOrders")
    public List<OrderResponse> getBuyOrders(HttpServletRequest request){
        Person personDetails = (Person) request.getAttribute("person");
        List<VoucherOrderDetail> orders = voucherOrderService.getBuyOrders(personDetails.getId());
        return this.orderTransformer.convertEntityListToResponse(orders,personDetails);
    }

    @GetMapping("/users/sellOrders")
    public List<OrderResponse> getSellOrders(HttpServletRequest request){
        Person personDetails = (Person) request.getAttribute("person");
        List<VoucherOrderDetail> orders = voucherOrderService.getSellOrders(personDetails.getId());
        return this.orderTransformer.convertEntityListToResponse(orders,personDetails);
    }
}
