package com.example.server.controllers;

import com.example.server.dto.request.PaymentOrderRequest;
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


    @PostMapping("/payment/create/cart-order")
    public PaymentOrderResponse createCartPaymentOrder(HttpServletRequest request, @RequestBody PaymentOrderRequest paymentOrderRequest) throws RazorpayException {
        Person personDetails = (Person) request.getAttribute("person");
        Long userId = personDetails.getId();
        CheckoutPageCost checkoutPageCost = cartService.getCartValue(userId);

        // take redeemed cost
        BigDecimal paymentValue = new BigDecimal(0);
        if(paymentOrderRequest.getHasRedeemed()){
            paymentValue = checkoutPageCost.getFinalCostAfterCoinRedeem();
        }
        else{
            paymentValue = checkoutPageCost.getFinalCost();
        }

        Order order = paymentService.createRazorPayOrder( paymentValue.toString() );
        String orderId = order.get("id");

        PaymentOrderResponse paymentOrderResponse = new PaymentOrderResponse();
        paymentOrderResponse.setOrderId(orderId);
        return paymentOrderResponse;
    }

    @PostMapping("/payment/create/voucher-order/{voucherId}")
    public PaymentOrderResponse createVoucherPaymentOrder(HttpServletRequest request,@PathVariable Long voucherId, @RequestBody PaymentOrderRequest paymentOrderRequest) throws RazorpayException {
        Person personDetails = (Person) request.getAttribute("person");
        Long buyerId = personDetails.getId();

        CheckoutPageCost checkoutPageCost = voucherService.getVoucherCostById(voucherId,buyerId);

        BigDecimal paymentValue = new BigDecimal(0);
        if(paymentOrderRequest.getHasRedeemed()){
            paymentValue = checkoutPageCost.getFinalCostAfterCoinRedeem();
        }
        else{
            paymentValue = checkoutPageCost.getFinalCost();
        }

        Order order = paymentService.createRazorPayOrder( paymentValue.toString() );
        String orderId = order.get("id");

        PaymentOrderResponse paymentOrderResponse = new PaymentOrderResponse();
        paymentOrderResponse.setOrderId(orderId);
        return paymentOrderResponse;
    }

    @GetMapping("payment/get/voucher-order/{voucherId}")
    public CheckoutPageCost voucherCost(HttpServletRequest request,@PathVariable Long voucherId){
        Person personDetails = (Person) request.getAttribute("person");
        Long buyerId = personDetails.getId();

         CheckoutPageCost checkoutPageCost = voucherService.getVoucherCostById(voucherId,buyerId);
        return checkoutPageCost;
    }
    @GetMapping("payment/get/cart-order/")
    public CheckoutPageCost voucherCost(HttpServletRequest request){
        Person personDetails = (Person) request.getAttribute("person");
        Long userId = personDetails.getId();
        CheckoutPageCost checkoutPageCost = cartService.getCartValue(userId);
        return checkoutPageCost;
    }

}
