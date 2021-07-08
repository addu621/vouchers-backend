package com.example.server.testServices;

import com.example.server.entities.Person;
import com.example.server.repositories.PersonRepo;
import com.example.server.services.PersonService;
import com.example.server.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class LoginTests {

    @MockBean
    private PersonRepo personRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    private Person person;

    private UserDetails userDetails;
    //signUp
    @Test
    public void saveTest(){
        Person person = new Person();
        person.setFirstName("Erwin");
        person.setLastName("Smith");
        person.setId(999L);
        person.setEmail("erwin@gmail.com");
        person.setPassword(bcryptEncoder.encode("erwin"));

        when(personRepo.save(person)).thenReturn(person);
        Map<String,Object> mp = new HashMap<>();
        mp.put("person", person);
        mp.put("message", "Please verify your email");
        assertEquals(mp,userService.save(person));
    }

    // user and admin login
    @Test
    public void loadUserByUsernameTest_notFound(){
        String userName = "potatoShit@gmail.com";
        when(personRepo.findByEmail(userName)).thenReturn(person);
        assertThrows(UsernameNotFoundException.class,()->userService.loadUserByUsername(userName));
    }

    @Test
    public void loadUserByUsernameTest(){
        Person person =new Person();
        person.setFirstName("Levi");
        person.setLastName("Ackerman");
        person.setEmail("levi@gmail.com");
        person.setPassword(bcryptEncoder.encode("levi"));

        when(personRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(person));

        Person actual = personService.findById(1L);
        assertEquals(person.getFirstName(),actual.getFirstName());
        assertEquals(person.getLastName(),actual.getLastName());
        assertEquals(person.getEmail(),actual.getEmail());
    }
}
