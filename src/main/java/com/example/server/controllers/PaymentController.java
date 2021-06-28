package com.example.server.controllers;

import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.response.PaymentOrderResponse;
import com.example.server.entities.Person;
import com.example.server.model.CheckoutPageCost;
import com.example.server.services.CartService;
import com.example.server.services.PaymentService;
import com.example.server.services.VoucherService;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    PaymentService paymentService;
    @Autowired
    CartService cartService;
    @Autowired
    VoucherService voucherService;


    @GetMapping("/payment/create/cart-order")
    public PaymentOrderResponse createCartPaymentOrder(HttpServletRequest request) throws RazorpayException {
        Person personDetails = (Person) request.getAttribute("person");
        Long userId = personDetails.getId();
        CheckoutPageCost checkoutPageCost = cartService.getCartValue(userId);

        Order order = paymentService.createRazorPayOrder( checkoutPageCost.getFinalCost().toString() );
        String orderId = order.get("id");

        PaymentOrderResponse paymentOrderResponse = new PaymentOrderResponse();
        paymentOrderResponse.setOrderId(orderId);
        return paymentOrderResponse;
    }

    @GetMapping("/payment/create/voucher-order/{voucherId}")
    public PaymentOrderResponse createVoucherPaymentOrder(HttpServletRequest request,@PathVariable Long voucherId) throws RazorpayException {
        Person personDetails = (Person) request.getAttribute("person");
        Long userId = personDetails.getId();

        BigDecimal amount = voucherService.getVoucherCostById(voucherId);

        Order order = paymentService.createRazorPayOrder( amount.toString() );
        String orderId = order.get("id");

        PaymentOrderResponse paymentOrderResponse = new PaymentOrderResponse();
        paymentOrderResponse.setOrderId(orderId);
        return paymentOrderResponse;
    }

//    @GetMapping("/cost")
//    public CheckoutPageCost cost(){
//        CheckoutPageCost checkoutPageCost = cartService.getCartValue(new Long(1));
//        return checkoutPageCost;
//    }

}
