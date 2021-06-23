package com.example.server.controllers;

import com.example.server.dto.request.PersonRequest;
import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.response.PersonResponse;
import com.example.server.entities.Person;
import com.example.server.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private PersonService personService;

    @PutMapping("/user/{userId}/update")
    public GenericResponse updateUser(@PathVariable Long userId,@RequestBody PersonRequest personRequest){

        GenericResponse genericResponse = new GenericResponse();
        Person updatedPerson = personService.updatePerson(userId,personRequest);

        if(updatedPerson!=null){
            genericResponse.setMessage("Success!!");
            genericResponse.setStatus(200);
        }

        return genericResponse;
    }
    @PostMapping("/profile")
    public PersonResponse updateUser(HttpServletRequest request) {
        Object personDetails = request.getAttribute("person");
        PersonResponse personResponse = new PersonResponse();
        copyProperties(personResponse,personDetails);
        return personResponse;
    }

}
