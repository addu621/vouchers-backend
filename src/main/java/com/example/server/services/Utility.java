package com.example.server.services;

import com.example.server.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;


@Service
public class Utility {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(Person person, String otp) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        String mailSubject="Verification Email";
        String mailContent="<div style=\"margin-left: 10%; \">" +
                "<h1 style=\"color: purple\">Verification Email</h1>" +
                "<div>" +
                "<p>" +
                "Hi "+person.getFirstName()+",<br>" +
                "Thanks for signing up at The Switch, you are just One step away of your email verification.<br>" +
                "Please Enter the below OTP within <b>10 minutes</b>." +
                "</p>" +
                "<h3 style=\"color: red\">"+otp+"</h3>" +
                "</div>" +
                "</div>";

        mimeMessageHelper.setFrom("adarshsingh621@gmail.com","The Switch");
        mimeMessageHelper.setSubject(mailSubject);
        mimeMessageHelper.setText(mailContent);
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
}
