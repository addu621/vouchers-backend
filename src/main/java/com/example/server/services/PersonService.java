package com.example.server.services;

import com.example.server.dto.request.PersonRequest;
import com.example.server.entities.Person;
import com.example.server.repositories.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class PersonService {
    @Autowired
    private PersonRepo personRepository;

    public Person updatePerson(Long userId, PersonRequest personRequest){
        Person oldPerson = personRepository.findById(userId).get();
        if(oldPerson==null){
            return null;
        }

        copyProperties(personRequest,oldPerson);
        personRepository.save(oldPerson);

        return oldPerson;
    }
}
