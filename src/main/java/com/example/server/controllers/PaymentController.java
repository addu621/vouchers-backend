package com.example.server.controllers;

import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.response.PaymentOrderResponse;
import com.example.server.services.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/create-payment-order")
    public PaymentOrderResponse createPaymentOrder() throws RazorpayException {
        Order order = paymentService.createRazorPayOrder( "100" );
        String orderId = order.get("id");

        PaymentOrderResponse paymentOrderResponse = new PaymentOrderResponse();
        paymentOrderResponse.setOrderId(orderId);
        return paymentOrderResponse;
    }

}
