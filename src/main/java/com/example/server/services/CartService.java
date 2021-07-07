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

import static org.springframework.beans.BeanUtils.copyProperties;

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
    private final VoucherService voucherService;

    public boolean isItemInBuyerCart(Long buyerId,Long voucherId){
        List<CartItem> cartItemList = cartItemRepository.findByCartIdAndVoucherId(buyerId,voucherId);
        if(cartItemList.size()==0){
            return false;
        }
        return true;
    }
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
            Voucher voucher1 = new Voucher();
            copyProperties(voucher,voucher1);
            voucher1.setSellingPrice(cartItem.getItemPrice());
            vouchers.add(voucher1);
        });
        return vouchers;
    }

    public List<Voucher> checkOutCart(long cartId,String transactionId, int noOfCoinsReedemed){
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        List<Voucher> vouchers = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal(0);
        cartItems.stream().forEach((CartItem c)->totalPrice.add(c.getItemPrice()));

        VoucherOrder voucherOrder = this.voucherOrderService.createOrder(cartId);   //cartId is same as buyer Id

        cartItems.forEach((CartItem cartItem)->{
            Voucher voucher = voucherRepository.findById(cartItem.getVoucherId()).get();
            voucher.setSellingPrice(cartItem.getItemPrice());
            VoucherOrderDetail voucherOrderDetail = this.voucherOrderService.addOrderItem(voucherOrder.getId(),cartItem.getVoucherId(),cartItem.getItemPrice());
            if(voucherOrderDetail!=null){
                this.removeItemFromCart(cartId,voucher.getId());
                vouchers.add(voucher);
            }
        });

        this.voucherOrderService.placeOrder(voucherOrder.getId(),transactionId,noOfCoinsReedemed);
        return vouchers;
    }

    public boolean removeItemFromCart(long cartId,long voucherId){
        return !cartItemRepository.deleteByCartIdAndVoucherId(cartId,voucherId).isEmpty();
    }

    public CheckoutPageCost getCartValue(Long cartId){
        List<CartItem> cartItemList = cartItemRepository.findByCartId(cartId);
        CheckoutPageCost checkoutPageCost = new CheckoutPageCost();
        Integer coinsInWallet = walletService.getWalletById(cartId).getCoins();
        BigDecimal totalPrice = new BigDecimal(0);
        for(CartItem cartItem: cartItemList){
            totalPrice = totalPrice.add(cartItem.getItemPrice());
        };
        CheckoutPageCost result = utilityService.calculateCheckoutCosts(totalPrice,coinsInWallet);
        return result;
    }

    public CheckoutPageCost getCartValue(Long cartId,int coinsToBeRedeemed){
        List<CartItem> cartItemList = cartItemRepository.findByCartId(cartId);
        CheckoutPageCost checkoutPageCost = new CheckoutPageCost();
        Integer coinsInWallet = walletService.getWalletById(cartId).getCoins();
        BigDecimal totalPrice = new BigDecimal(0);
        for(CartItem cartItem: cartItemList){
            totalPrice = totalPrice.add(cartItem.getItemPrice());
        };
        CheckoutPageCost result = utilityService.calculateCheckoutCosts(totalPrice,coinsInWallet,coinsToBeRedeemed);
        return result;
    }

    public Boolean canAddToCart(Long buyerId,Long voucherId){
        Long sellerId = voucherService.getSellerIdByVoucherId(voucherId);

        // check if current buyer is not seller
        if(sellerId==buyerId){
            return false;
        }

        // check if current voucher is already sold
        if(voucherService.isVoucherSold(voucherId)){
            return false;
        }

        if(!isItemInBuyerCart(buyerId,voucherId)){
            return true;
        }
        return false;
    }
}

