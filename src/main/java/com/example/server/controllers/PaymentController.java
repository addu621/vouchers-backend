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
        CheckoutPageCost checkoutPageCost = cartService.getCartValue(userId,paymentOrderRequest.getRedeemedCoins());

        // take redeemed cost
        BigDecimal paymentValue = new BigDecimal(0);
        if(paymentOrderRequest.getRedeemedCoins()>0){
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

        CheckoutPageCost checkoutPageCost = voucherService.getVoucherCostById(voucherId,buyerId,paymentOrderRequest.getRedeemedCoins());

        BigDecimal paymentValue = new BigDecimal(0);
        if(paymentOrderRequest.getRedeemedCoins()>0){
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

    @GetMapping("payment/get/voucher-order/{voucherId}/redeem/{redeemCoins}")
    public CheckoutPageCost voucherCostByVoucherId(HttpServletRequest request,@PathVariable Long voucherId,@PathVariable Integer redeemCoins){
        Person personDetails = (Person) request.getAttribute("person");
        Long buyerId = personDetails.getId();

         CheckoutPageCost checkoutPageCost = voucherService.getVoucherCostById(voucherId,buyerId,redeemCoins);
        return checkoutPageCost;
    }
    @GetMapping("payment/get/cart-order/{redeemCoins}")
    public CheckoutPageCost voucherCostByCartId(HttpServletRequest request,@PathVariable Integer redeemCoins){
        Person personDetails = (Person) request.getAttribute("person");
        Long userId = personDetails.getId();
        CheckoutPageCost checkoutPageCost = cartService.getCartValue(userId,redeemCoins);
        return checkoutPageCost;
    }

//    @GetMapping("payment/disburse/seller")
//    public String disbursePaymentToSeller(HttpServletRequest request){
//        Person personDetails = (Person) request.getAttribute("person");
//        Long userId = personDetails.getId();
//        CheckoutPageCost checkoutPageCost = cartService.getCartValue(userId);
//        return checkoutPageCost;
//    }

}
