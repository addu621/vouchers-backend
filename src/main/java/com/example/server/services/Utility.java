package com.example.server.services;

import com.example.server.entities.Issue;
import com.example.server.entities.Person;
import com.example.server.entities.VoucherOrder;
import com.example.server.entities.VoucherOrderDetail;
import com.example.server.model.CheckoutPageCost;
import com.example.server.repositories.PersonRepo;
import com.example.server.repositories.VoucherOrderDetailRepository;
import com.example.server.repositories.VoucherOrderRepository;
import com.example.server.repositories.VoucherRepository;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Service
public class Utility {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VoucherOrderRepository voucherOrderRepository;

    @Autowired
    private VoucherOrderDetailRepository voucherOrderDetailRepository;

    @Autowired
    private PersonRepo personRepo;

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public BigDecimal  calculatePercentage(BigDecimal value, BigDecimal percent){
        return value.multiply(percent).divide(ONE_HUNDRED).setScale(2, RoundingMode.HALF_UP);
    }

    public CheckoutPageCost calculateCheckoutCosts(BigDecimal totalPrice,Integer loyaltyCoinsInWallet){
        CheckoutPageCost checkoutPageCost = new CheckoutPageCost();

        BigDecimal tax = calculatePercentage(totalPrice,new BigDecimal(2.5));
        BigDecimal finalCost = tax.add(totalPrice);
        finalCost = finalCost.setScale(2, RoundingMode.HALF_UP);

        Integer loyaltyCoinsEarned =calculatePercentage(totalPrice,new BigDecimal(5)).setScale(0, RoundingMode.UP).intValue();
        Integer existingLoyaltyCoinsValue = loyaltyCoinsInWallet/2;
        Integer maxCoinsRedeemedValue = Math.min(totalPrice.intValue(),existingLoyaltyCoinsValue);
        BigDecimal finalCostAfterCoinRedeem = finalCost.subtract(new BigDecimal(maxCoinsRedeemedValue)).max(new BigDecimal(0.50));
        Integer remainingCoins = loyaltyCoinsInWallet - maxCoinsRedeemedValue*2;

        checkoutPageCost.setItemsValue(totalPrice);
        checkoutPageCost.setTaxCalculated(tax);
        checkoutPageCost.setLoyaltyCoinsInWallet(loyaltyCoinsInWallet);
        checkoutPageCost.setLoyaltyCoinsEarned(loyaltyCoinsEarned);
        checkoutPageCost.setExistingLoyaltyCoinsValue(existingLoyaltyCoinsValue);
        checkoutPageCost.setFinalCost(finalCost);
        checkoutPageCost.setFinalCostAfterCoinRedeem(finalCostAfterCoinRedeem);
        checkoutPageCost.setCoinBalanceAfterRedemption(remainingCoins);

        return checkoutPageCost;
    }
    public void sendMail(Person person, String otp) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        String mailSubject="Verification Email";
        String mailContent="<div style=\"margin-left: 10%; \">" +
                "<h1 style=\"color: purple\">Verification Email</h1>" +
                "<div>" +
                "<p>" +
                "Hi "+person.getFirstName()+",<br>" +
                "Thanks for signing up at Voucher Money, you are just One step away of your email verification.<br>" +
                "Please Enter the below OTP within <b>5 minutes</b>." +
                "</p>" +
                "<h3 style=\"color: red\">"+otp+"</h3>" +
                "</div>" +
                "</div>";

        mimeMessageHelper.setFrom("studiocars2021@gmail.com","Voucher Money");
        mimeMessageHelper.setSubject(mailSubject);
        mimeMessageHelper.setText(mailContent,true);
        mimeMessageHelper.setTo(person.getEmail());

        javaMailSender.send(mimeMessage);

    }

    public String getOtp(int size) {
        String otp = "";

        for(int i=0;i<size;i++)
        {
            otp += ((int)(Math.random()*100))%10;
        }

        return otp;
    }

    public void forgotPasswordMail(Person person, String otp) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        String mailSubject="Forgot Password Email";
        String mailContent="<div style=\"margin-left: 10%; \">" +
                "<h1 style=\"color: purple\">Forgot Password Email</h1>" +
                "<div>" +
                "<p>" +
                "Hi "+person.getFirstName()+",<br>" +
                "We have received your password updating request.<br>" +
                "Please Enter the below OTP within <b>5 minutes</b> to renew your password." +
                "</p>" +
                "<h3 style=\"color: red\">"+otp+"</h3>" +
                "</div>" +
                "</div>";

        mimeMessageHelper.setFrom("studiocars2021@gmail.com","Voucher Money");
        mimeMessageHelper.setSubject(mailSubject);
        mimeMessageHelper.setText(mailContent,true);
        mimeMessageHelper.setTo(person.getEmail());

        javaMailSender.send(mimeMessage);
    }

    public void issueClosedMail(Issue issue) throws MessagingException, UnsupportedEncodingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        VoucherOrderDetail voucherOrderDetail = voucherOrderDetailRepository.findById(issue.getOrderItemId()).get();
        VoucherOrder voucherOrder = voucherOrderRepository.findById(voucherOrderDetail.getOrderId()).get();

        Person person = personRepo.findById(voucherOrder.getBuyerId()).get();

        String mailSubject="Issue Closed Email";
        String mailContent="<div style=\"margin-left: 10%; \">" +
                "<h1 style=\"color: purple\">Issue Closed Email</h1>" +
                "<div>" +
                "<p>" +
                "Hi "+person.getFirstName()+",<br>" +
                "Your Issue with Issue-Id :- " + issue.getIssueId() + " <br>" +
                "With Issue Description :- <br>" + issue.getComment() + " has been resolved.<br>" +
                "<br><br> If you are not satisfied with the resolution you can feel free to reply us back on this mail!!! <br>" +
                "<br><br><br> Thanks and Regards,<br>" +
                "Team Voucher-Money<br>" +
                "</p>" +
                "</div>" +
                "</div>";

        mimeMessageHelper.setFrom("studiocars2021@gmail.com","Voucher Money");
        mimeMessageHelper.setSubject(mailSubject);
        mimeMessageHelper.setText(mailContent,true);
        mimeMessageHelper.setTo(person.getEmail());

        javaMailSender.send(mimeMessage);
    }

    public String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public Date parseDate(String date) throws ParseException {
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Date result = formatter.parse(date);
        return result;
    }
}
