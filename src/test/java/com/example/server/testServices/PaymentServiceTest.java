package com.example.server.testServices;

import com.example.server.services.PaymentService;
import com.razorpay.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @Test
    public void canCreateRazorPayOrder_whenAmountIsValid()  throws Exception {
        String amount = "100";
        Order order = Mockito.mock(Order.class);

        Integer result = paymentService.createRazorPayOrder(amount).get("amount");
        Integer expected = 10000;
        Assertions.assertEquals(result,expected);

    }

    @Test
    public void convertRupeeToPaise()  throws Exception {
        String amount = "100";
        String rupee = paymentService.convertRupeeToPaise(amount);

        String expected = "10000";
        Assertions.assertEquals(rupee,expected);

    }
}
