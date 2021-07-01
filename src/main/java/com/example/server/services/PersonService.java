package com.example.server.services;

import com.example.server.dto.request.PersonRequest;
import com.example.server.dto.response.SellerRatingResponse;
import com.example.server.entities.Person;
import com.example.server.entities.SellerRating;
import com.example.server.repositories.BlockedUsersRepository;
import com.example.server.repositories.PersonRepo;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class PersonService {
    @Autowired
    private PersonRepo personRepository;

    @Autowired
    private BlockedUsersRepository blockedUsersRepository;

    public Person updatePerson(Long userId, PersonRequest personRequest){
        Person oldPerson = personRepository.findById(userId).get();
        if(oldPerson==null){
            return null;
        }

        copyProperties(personRequest, oldPerson, getNullPropertyNames(personRequest));
        personRepository.save(oldPerson);

        return oldPerson;
    }

    public boolean verifyUser(long userId){
        if(personRepository.findById(userId)==null) return false;
        Person person = personRepository.findById(userId).get();
        if(person.getSsn()==null) return false;
        person.setSsnVerified(true);
        personRepository.save(person);
        return true;
    }

    public boolean isUserBlocked(long userId){
        return blockedUsersRepository.findById(String.valueOf(userId))!=null;
    }

    public List<Person> getAllPersons(){
        List<Person> persons = (List<Person>) personRepository.findAll();
        return persons.stream().filter((Person p)->!isUserBlocked(p.getId())).collect(Collectors.toList());
    }

    public List<Person> getAllKycSubmittedPersons(){
        List<Person> persons =  personRepository.findBySsnNotNullAndSsnVerifiedFalse();
        return persons.stream().filter((Person p)->!isUserBlocked(p.getId())).collect(Collectors.toList());
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

    public Person findById(Long id){
        return personRepository.findById(id).get();
    }
}
