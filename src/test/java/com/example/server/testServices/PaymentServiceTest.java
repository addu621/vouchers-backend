package com.example.server.testServices;

import com.example.server.entities.CartItem;
import com.example.server.entities.Voucher;
import com.example.server.entities.Wallet;
import com.example.server.model.CheckoutPageCost;
import com.example.server.repositories.VoucherRepository;
import com.example.server.services.PaymentService;
import com.example.server.services.Utility;
import com.example.server.services.VoucherService;
import com.example.server.services.WalletService;
import com.razorpay.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @InjectMocks
    VoucherService voucherService;

    @Mock
    WalletService walletService;

    @InjectMocks
    Utility utilityService;

    @Mock
    VoucherRepository voucherRepository;

    @Test
    public void canCreateRazorPayOrder_whenAmountIsValid()  throws Exception {
        String amount = "100";
        Order order = Mockito.mock(Order.class);

        Integer result = paymentService.createRazorPayOrder(amount).get("amount");
        Integer expected = 10000;
        Assertions.assertEquals(result,expected);

    }

    @Test
    public void convertRupeeToPaiseTest()  throws Exception {
        String amount = "100";
        String rupee = paymentService.convertRupeeToPaise(amount);

        String expected = "10000";
        Assertions.assertEquals(rupee,expected);

    }

    @Test
    public void getVoucherCostByIdTest() throws Exception{
        Long voucherId = 1l;
        Long buyerId = 1l;
        Integer coinsToBeRedeemed = 10;
        Integer existingCoins = 20;
        BigDecimal totalPrice = new BigDecimal(100.0);

        Voucher voucher = Mockito.mock(Voucher.class);
        voucher.setSellingPrice(totalPrice);

        Mockito.when(voucherRepository.findById(voucherId))
                .thenReturn(java.util.Optional.of(voucher));
        Mockito.when(walletService.getWalletById(buyerId))
                .thenReturn(new Wallet());

        CheckoutPageCost result = utilityService.calculateCheckoutCosts(totalPrice,existingCoins,coinsToBeRedeemed);

        CheckoutPageCost expected = new CheckoutPageCost();
        expected.setItemsValue(new BigDecimal(100));
        expected.setTaxCalculated(new BigDecimal(2.50).setScale(2, RoundingMode.HALF_UP));
        expected.setLoyaltyCoinsInWallet(20);
        expected.setLoyaltyCoinsEarned(5);
        expected.setExistingLoyaltyCoinsValue(5);
        expected.setCoinBalanceAfterRedemption(10);
        expected.setFinalCost(new BigDecimal(102.50).setScale(2, RoundingMode.HALF_UP));
        expected.setFinalCostAfterCoinRedeem(new BigDecimal(97.50).setScale(2, RoundingMode.HALF_UP));

        assertEquals(result,expected);

    }

    @Test
    public void getVoucherCostByIdTest_whenTotalPriceIsLessThanRedeemValue() throws Exception{
        Long voucherId = 1l;
        Long buyerId = 1l;
        Integer coinsToBeRedeemed = 10;
        Integer existingCoins = 20;
        BigDecimal totalPrice = new BigDecimal(3.0);

        Voucher voucher = Mockito.mock(Voucher.class);
        voucher.setSellingPrice(totalPrice);

        Mockito.when(voucherRepository.findById(voucherId))
                .thenReturn(java.util.Optional.of(voucher));
        Mockito.when(walletService.getWalletById(buyerId))
                .thenReturn(new Wallet());

        CheckoutPageCost result = utilityService.calculateCheckoutCosts(totalPrice,existingCoins,coinsToBeRedeemed);
        System.out.println(result);
        CheckoutPageCost expected = new CheckoutPageCost();
        expected.setItemsValue(new BigDecimal(3));
        expected.setTaxCalculated(new BigDecimal(0.08).setScale(2, RoundingMode.HALF_UP));
        expected.setLoyaltyCoinsInWallet(20);
        expected.setLoyaltyCoinsEarned(1);
        expected.setExistingLoyaltyCoinsValue(5);
        expected.setCoinBalanceAfterRedemption(14);
        expected.setFinalCost(new BigDecimal(3.08).setScale(2, RoundingMode.HALF_UP));
        expected.setFinalCostAfterCoinRedeem(new BigDecimal(0.50));

        assertEquals(result,expected);

    }

}
