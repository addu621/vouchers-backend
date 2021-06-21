package com.example.server.services;

import com.example.server.entities.Person;
import com.example.server.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PersonRepo personRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

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

    public Person save(Person person) {
        person.setPassword(bcryptEncoder.encode(person.getPassword()));
        System.out.println(person);
        return personRepo.save(person);
    }
}
