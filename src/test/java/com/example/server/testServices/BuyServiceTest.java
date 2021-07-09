package com.example.server.testServices;

import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.CartItem;
import com.example.server.entities.Notification;
import com.example.server.entities.VoucherDeal;
import com.example.server.repositories.VoucherDealRepository;
import com.example.server.services.NotificationService;
import com.example.server.services.VoucherDealService;
import com.example.server.services.VoucherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BuyServiceTest {
    @InjectMocks
    VoucherDealService voucherDealService;
    @Mock
    VoucherService voucherService;
    @Mock
    VoucherDealRepository voucherDealRepository;
    @Mock
    NotificationService notificationService;

    @Test
    public void canQuotePrice_whenVoucherIsSold(){
        Long sellerId=1l;
        Long voucherId=1l;
        Long buyerId=2l;
        BigDecimal quotedPrice = new BigDecimal(10.0);

        Mockito.when(voucherService.getSellerIdByVoucherId(voucherId))
                .thenReturn(sellerId);
        Mockito.when(voucherService.isVoucherSold(voucherId)).thenReturn(true);

        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setStatus(404);
        expectedResponse.setMessage("Listing is already sold, you cannot bid now!!");

        GenericResponse actualResponse = voucherDealService.quotePrice(buyerId,voucherId,quotedPrice);


        Assertions.assertEquals(actualResponse,expectedResponse);
    }

    @Test
    public void canQuotePrice_whenVoucherIsQuotedFirstTime(){
        Long sellerId=1l;
        Long voucherId=1l;
        Long buyerId=2l;
        BigDecimal quotedPrice = new BigDecimal(10.0);

        List<VoucherDeal> voucherDealList = new ArrayList<>();
        Notification notification = Mockito.mock(Notification.class);

        Mockito.when(voucherService.getSellerIdByVoucherId(voucherId))
                .thenReturn(sellerId);
        Mockito.when(voucherService.isVoucherSold(voucherId)).thenReturn(false);
        Mockito.when(voucherDealRepository.findByVoucherIdAndBuyerId(voucherId,buyerId))
                .thenReturn(voucherDealList);
        Mockito.when(notificationService.createNewNotification(notification))
                .thenReturn(notification);
        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setStatus(200);
        expectedResponse.setMessage("You have placed bid for this listing");

        GenericResponse actualResponse = voucherDealService.quotePrice(buyerId,voucherId,quotedPrice);


        Assertions.assertEquals(actualResponse,expectedResponse);
    }

    @Test
    public void canQuotePrice_whenVoucherIsAlreadyQuoted(){
        Long sellerId=1l;
        Long voucherId=1l;
        Long buyerId=2l;
        BigDecimal quotedPrice = new BigDecimal(10.0);

        List<VoucherDeal> voucherDealList = new ArrayList<>();
        Notification notification = Mockito.mock(Notification.class);
        VoucherDeal voucherDeal = Mockito.mock(VoucherDeal.class);
        voucherDealList.add(voucherDeal);

        Mockito.when(voucherService.getSellerIdByVoucherId(voucherId))
                .thenReturn(sellerId);
        Mockito.when(voucherService.isVoucherSold(voucherId)).thenReturn(false);
        Mockito.when(voucherDealRepository.findByVoucherIdAndBuyerId(voucherId,buyerId))
                .thenReturn(voucherDealList);
        Mockito.when(notificationService.createNewNotification(notification))
                .thenReturn(notification);
        GenericResponse expectedResponse = new GenericResponse();
        expectedResponse.setStatus(200);
        expectedResponse.setMessage("Your new bid has been placed bid for this listing");

        GenericResponse actualResponse = voucherDealService.quotePrice(buyerId,voucherId,quotedPrice);


        Assertions.assertEquals(actualResponse,expectedResponse);
    }

}
