package com.example.server.testServices;

import com.example.server.entities.CartItem;
import com.example.server.entities.Voucher;
import com.example.server.repositories.CartItemRepository;
import com.example.server.repositories.VoucherRepository;
import com.example.server.services.CartService;
import com.example.server.services.PersonService;
import com.example.server.services.VoucherService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class CartServiceTest {

    @Mock
    VoucherService voucherService;

    @Mock
    CartItemRepository cartItemRepository;

    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    CartService cartService;

    @Test
    public void canAddToCartTest_whenBuyerIsSeller_thenFalse()  throws Exception {
        Long sellerId=1l;
        Long buyerId=1l;
        Long voucherId=1l;

        Mockito.when(voucherService.getSellerIdByVoucherId(1l)).thenReturn(1l);
        assertFalse(cartService.canAddToCart(buyerId,voucherId));
    }
    @Test
    public void canAddToCartTest_whenVoucherIsSold_thenFalse()  throws Exception {
        Long sellerId=1l;
        Long buyerId=1l;
        Long voucherId=1l;

        Mockito.when(voucherService.getSellerIdByVoucherId(1l)).thenReturn(2l);
        Mockito.when(voucherService.isVoucherSold(1l)).thenReturn(true);
        assertFalse(cartService.canAddToCart(buyerId,voucherId));
    }



    @Test
    public void canAddToCartTest_whenVoucherIsInUserCart_thenFalse()  throws Exception {
        Long sellerId=1l;
        Long buyerId=1l;
        Long voucherId=1l;

        // mock cartItemsList
        List<CartItem> cartItemsList = new ArrayList<>();
        CartItem cartItem = Mockito.mock(CartItem.class);
        cartItemsList.add(cartItem);

        Mockito.when(voucherService.getSellerIdByVoucherId(1l)).thenReturn(2l);
        Mockito.when(voucherService.isVoucherSold(1l)).thenReturn(false);
        Mockito.when(cartItemRepository.findByCartIdAndVoucherId(buyerId,voucherId)).thenReturn(cartItemsList);
        assertFalse(cartService.canAddToCart(buyerId,voucherId));
    }

    @Test
    public void addItemToCartTest_when_voucherIsAlreadyInCart_thenFalse(){
        Long voucherId=1l;
        Long cartId=1l;

        // mock voucher
        Voucher voucher = new Voucher();
        voucher.setId(voucherId);

        // mock cartItemsList
        List<CartItem> cartItemsList = new ArrayList<>();
        System.out.println(cartItemsList.size());
        CartItem cartItem = new CartItem();

        Mockito.when(voucherRepository.findById(voucherId)).thenReturn(java.util.Optional.of(voucher));
        Mockito.when(cartItemRepository.findByCartIdAndVoucherId(cartId,voucherId)).thenReturn(new ArrayList<>());
        Mockito.when(cartItemRepository.save(cartItem)).thenReturn(cartItem);

        assertFalse(cartService.addItemToCart(cartId,voucherId));
    }
}
