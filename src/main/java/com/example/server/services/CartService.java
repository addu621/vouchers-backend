package com.example.server.services;

import com.example.server.entities.*;
import com.example.server.enums.TransactionType;
import com.example.server.model.CheckoutPageCost;
import com.example.server.repositories.CartItemRepository;
import com.example.server.repositories.CartRepository;
import com.example.server.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private final Utility utilityService;
    private final WalletService walletService;


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

    public List<Voucher> checkOutCart(long cartId,String transactionId){
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        List<Voucher> vouchers = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0);
        cartItems.stream().forEach((CartItem c)->totalPrice.add(c.getItemPrice()));

        Transaction transaction = this.transactionService.addTransaction(transactionId,cartId,TransactionType.ORDER_PLACED,totalPrice);
        VoucherOrder voucherOrder = this.voucherOrderService.createOrder(cartId,transaction.getId());   //cartId is same as buyer Id

        cartItems.forEach((CartItem cartItem)->{
            Voucher voucher = voucherRepository.findById(cartItem.getVoucherId()).get();
            voucher.setSellingPrice(cartItem.getItemPrice());
            VoucherOrderDetail voucherOrderDetail = this.voucherOrderService.addOrderItem(voucherOrder.getId(),cartItem.getVoucherId(),cartItem.getItemPrice());
            if(voucherOrderDetail!=null){
                this.removeItemFromCart(cartId,voucher.getId());
                vouchers.add(voucher);
            }
        });

        this.voucherOrderService.placeOrder(voucherOrder.getId());
        return vouchers;
    }

    public boolean removeItemFromCart(long cartId,long voucherId){
        return !cartItemRepository.deleteByCartIdAndVoucherId(cartId,voucherId).isEmpty();
    }

    public CheckoutPageCost getCartValue(Long cartId){
        List<CartItem> cartItemList = cartItemRepository.findByCartId(cartId);
        CheckoutPageCost checkoutPageCost = new CheckoutPageCost();

        BigDecimal totalPrice = new BigDecimal(0);
        cartItemList.forEach((CartItem cartItem)-> {
            checkoutPageCost.setItemsValue((totalPrice.add(cartItem.getItemPrice())));
        });

        BigDecimal tax = utilityService.calculatePercentage(checkoutPageCost.getItemsValue(),new BigDecimal(2.5));
        BigDecimal finalCost = tax.setScale(0, RoundingMode.UP).add(checkoutPageCost.getItemsValue());
        Integer loyaltyCoins = utilityService.calculatePercentage(checkoutPageCost.getItemsValue(),new BigDecimal(5)).setScale(0, RoundingMode.UP).intValue();

        checkoutPageCost.setTaxCalculated(tax);
        checkoutPageCost.setLoyaltyCoins(loyaltyCoins);
        checkoutPageCost.setFinalCost(finalCost);

        return checkoutPageCost;
    }
}

