package com.example.server.testServices;

import com.example.server.entities.Person;
import com.example.server.repositories.PersonRepo;
import com.example.server.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TestUserService {

    @MockBean
    private PersonRepo personRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

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
}
