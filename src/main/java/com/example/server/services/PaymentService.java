package com.example.server.services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PaymentService {

    private RazorpayClient client;
    private static final String SECRET_ID = "rzp_test_739cyzNZMGyUcF";
    private static final String SECRET_KEY = "t8uajrG9wq8h0ZiDbtKgtf81";

    PaymentService() throws RazorpayException {
        this.client =  new RazorpayClient(SECRET_ID, SECRET_KEY);
    }

    public Order createRazorPayOrder(String amount) throws RazorpayException {


        JSONObject options = new JSONObject();
        options.put("amount", convertRupeeToPaise(amount));
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", 1); // You can enable this if you want to do Auto Capture.
        return client.Orders.create(options);
    }

    public String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();

    }

//    public disbursePayment
}
