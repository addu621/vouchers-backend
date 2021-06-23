package com.example.server.services;

import com.example.server.dto.request.PersonRequest;
import com.example.server.entities.Person;
import com.example.server.repositories.PersonRepo;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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

        copyProperties(personRequest, oldPerson, getNullPropertyNames(personRequest));
        personRepository.save(oldPerson);

        return oldPerson;
    }

    public static String[] getNullPropertyNames (Object source) {
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

    public Person findByEmail(String email){
        return personRepository.findByEmail(email);
    }

}
