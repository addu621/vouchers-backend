package com.example.server.services;

import com.example.server.entities.UserDao;
import com.example.server.entities.UserDto;
import com.example.server.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDao user = userRepo.findByUsername(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("User: "+ username + " not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());


//        System.out.println(username);
//        com.example.server.entities.User user = userRepo.findByUserEmail(username);
//        System.out.println(username);
//        if(username.equals("Adarsh"))
//        {
//            System.out.println(1);
//        }
//        if(user==null)
//        {
//            throw new UsernameNotFoundException("User: "+ username + " not found");
//        }
//            return new org.springframework.security.core.userdetails.User(user.getUserEmail(),user.getUserPassword(),new ArrayList<>());


//        if(username.equals("Adarsh")) {
//            return new User("Adarsh","Adarsh@123",new ArrayList<>());
//        }
//        else {
//            throw new UsernameNotFoundException("User not found");
//        }
     }

     public UserDao save(UserDto user) {
        UserDao newUser = new UserDao();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userRepo.save(newUser);
     }
}
