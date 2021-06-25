package com.example.server.services;

import com.example.server.entities.*;
import com.example.server.enums.TransactionType;
import com.example.server.repositories.CartItemRepository;
import com.example.server.repositories.CartRepository;
import com.example.server.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final VoucherRepository voucherRepository;
    private final VoucherOrderService voucherOrderService;
    private final TransactionService transactionService;

    public boolean addItemToCart(long cartId,long voucherId){
        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartId);
        cartItem.setVoucherId(voucherId);
        Voucher voucher = voucherRepository.findById(voucherId).get();
        cartItem.setItemPrice(voucher.getSellingPrice());
        if(!cartItemRepository.findByCartIdAndVoucherId(cartId,voucherId).isEmpty()){
            return false;
        }
        return cartItemRepository.save(cartItem)!=null;
    }

    public boolean addItemToCart(long cartId, long voucherId, BigDecimal itemPrice){
        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartId);
        cartItem.setVoucherId(voucherId);
        cartItem.setItemPrice(itemPrice);
        if(!cartItemRepository.findByCartIdAndVoucherId(cartId,voucherId).isEmpty()){
            return false;
        }
        return cartItemRepository.save(cartItem)!=null;
    }

    public boolean createCart(long cartId){
        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setCreatedOn(new Date());
        return this.cartRepository.save(cart)!=null;
    }

    public List<Voucher> getVouchersByCartId(long cartId){
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        List<Voucher> vouchers = new ArrayList<>();
        cartItems.forEach((CartItem cartItem)->{
            Voucher voucher = voucherRepository.findById(cartItem.getVoucherId()).get();
            voucher.setSellingPrice(cartItem.getItemPrice());
            vouchers.add(voucher);
        });
        return vouchers;
    }

    public List<Voucher> checkOutCart(long cartId){
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        List<Voucher> vouchers = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0);
        System.out.print("bhai ye price h:"+totalPrice);
        cartItems.stream().forEach((CartItem c)->totalPrice.add(c.getItemPrice()));
        Transaction transaction = this.transactionService.addTransaction(cartId,TransactionType.ITEMS_PURCHASED,totalPrice);
        cartItems.forEach((CartItem cartItem)->{
            Voucher voucher = voucherRepository.findById(cartItem.getVoucherId()).get();
            voucher.setSellingPrice(cartItem.getItemPrice());
            VoucherOrder voucherOrder = this.voucherOrderService.addOrder(cartId,voucher.getId(),cartItem.getItemPrice(),transaction.getId());   //cartId is same as buyer Id
            if(voucherOrder!=null){
                this.removeItemFromCart(cartId,voucher.getId());
                vouchers.add(voucher);
            }
        });
        return vouchers;
    }

    public boolean removeItemFromCart(long cartId,long voucherId){
        return !cartItemRepository.deleteByCartIdAndVoucherId(cartId,voucherId).isEmpty();
    }
}
