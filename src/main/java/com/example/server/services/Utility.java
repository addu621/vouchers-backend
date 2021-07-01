package com.example.server.services;

import com.example.server.entities.Person;
import com.example.server.model.CheckoutPageCost;
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

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public BigDecimal  calculatePercentage(BigDecimal value, BigDecimal percent){
        return value.multiply(percent).divide(ONE_HUNDRED).setScale(2, RoundingMode.HALF_UP);
    }

    public CheckoutPageCost calculateCheckoutCosts(BigDecimal totalPrice){
        CheckoutPageCost checkoutPageCost = new CheckoutPageCost();

        BigDecimal tax = calculatePercentage(totalPrice,new BigDecimal(2.5));
        BigDecimal finalCost = tax.add(totalPrice);
        finalCost = finalCost.setScale(2, RoundingMode.HALF_UP);
        Integer loyaltyCoins =calculatePercentage(totalPrice,new BigDecimal(5)).setScale(0, RoundingMode.UP).intValue();

        checkoutPageCost.setItemsValue(totalPrice);
        checkoutPageCost.setTaxCalculated(tax);
        checkoutPageCost.setLoyaltyCoins(loyaltyCoins);
        checkoutPageCost.setFinalCost(finalCost);

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
