package com.example.server.services;

import com.example.server.entities.Person;
import com.example.server.entities.VoucherCategory;
import com.example.server.entities.VoucherCompany;
import com.example.server.entities.VoucherType;
import com.example.server.repositories.PersonRepo;
import com.example.server.repositories.VoucherCategoryRepo;
import com.example.server.repositories.VoucherCompanyRepo;
import com.example.server.repositories.VoucherTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private VoucherCategoryRepo voucherCategoryRepo;

    @Autowired
    private VoucherCompanyRepo voucherCompanyRepo;

    @Autowired
    private VoucherTypeRepo voucherTypeRepo;

    @Autowired
    private Utility utility;

    @Autowired
    private CartService cartService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        com.example.server.entities.User user = userRepo.findByUserEmail(username);
        System.out.println(username);
        Person person = personRepo.findByEmail(username);
        System.out.println(person);
        if(person==null)
        {
            throw new UsernameNotFoundException("User: "+ username + " not found");
        }

        return new org.springframework.security.core.userdetails.User(person.getEmail(),person.getPassword(),new ArrayList<>());

     }

//    public com.example.server.entities.User save(com.example.server.entities.User user) {
//        com.example.server.entities.User user1 = new com.example.server.entities.User();
//        user1.setUserEmail(user.getUserEmail());
//        user1.setUserPassword(bcryptEncoder.encode(user.getUserPassword()));
//        return userRepo.save(user1);
//    }

    public Map save(Person person) {
        String otp = utility.getOtp(6);
        person.setOtp(otp);
        Map<String,Object> mp = new HashMap<>();
        try{
            Person oldPerson = personRepo.findByEmail(person.getEmail());
            if(oldPerson!=null)
            {
                throw new Exception("User with this Email Id already exists!!!");
            }

            person.setPassword(bcryptEncoder.encode(person.getPassword()));
            person.setIsOtpVerified(false);
            person.setIsAdmin(false);
            System.out.println(person);
            long id = personRepo.save(person).getId();
            cartService.createCart(id);
            mp.put("person", person);
            mp.put("message", "Please verify your email");
            utility.sendMail(person, otp);
        } catch (Exception e) {
            e.printStackTrace();
            mp.put("Message",e.getMessage());
        }
        return mp;
    }

    public Map otpVerify(String email, String otp) {
        Map<String,Object> mp =new HashMap<>();
        try {
            Person person = personRepo.findByEmail(email);
            if(person != null) {
                if(otp.equals(person.getOtp()))
                {
                    person.setIsOtpVerified(true);
                    personRepo.save(person);
                    mp.put("message","person otp verified");
                    mp.put("person", person);
                }
                else {
                    mp.put("message","Incorrect OTP");
                }
            }
            else {
                throw new Exception("User with this email do not exist!!!");
            }
        } catch (Exception e) {
            mp.put("error",e.getMessage());
            e.printStackTrace();
        }
        return mp;
    }
}
