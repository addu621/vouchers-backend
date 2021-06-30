package com.example.server.controllers;

import com.example.server.dto.request.PersonRequest;
import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.response.PersonResponse;
import com.example.server.dto.transformer.PersonTransformer;
import com.example.server.entities.Person;
import com.example.server.entities.Voucher;
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

    @Autowired
    private PersonTransformer personTransformer;

    @PutMapping("/user/update")
    public GenericResponse updateUser(HttpServletRequest request,@RequestBody PersonRequest personRequest){

        GenericResponse genericResponse = new GenericResponse();
        Person personDetails = (Person) request.getAttribute("person");

        Person updatedPerson = personService.updatePerson(personDetails.getId(),personRequest);

        if(updatedPerson!=null){
            genericResponse.setMessage("Success!!");
            genericResponse.setStatus(200);
        }

        return genericResponse;
    }

    @GetMapping("/profile")
    public PersonResponse updateUser(HttpServletRequest request) {
        Object personDetails = request.getAttribute("person");
        PersonResponse personResponse = new PersonResponse();
        copyProperties(personDetails,personResponse);
        return personResponse;
    }

    @GetMapping("/users")
    public List<PersonResponse> getAllUsers(){
        List<Person> persons = personService.getAllPersons();
        return personTransformer.convertEntityListToResponseList(persons);
    }

    @GetMapping("/users/kycSubmitted")
    public List<PersonResponse> getAllKycSubmittedUsers(){
        List<Person> persons = personService.getAllKycSubmittedPersons();
        return personTransformer.convertEntityListToResponseList(persons);
    }
}
