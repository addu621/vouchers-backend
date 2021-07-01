package com.example.server.dto.transformer;

import com.example.server.dto.response.PersonResponse;
import com.example.server.entities.Person;
import com.example.server.services.VoucherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@AllArgsConstructor
public class PersonTransformer {

    private final VoucherService voucherService;

    public PersonResponse convertEntityToResponse(Person person){
        PersonResponse personResponse = new PersonResponse();
        copyProperties(person,personResponse);
        personResponse.setSales(voucherService.getSellVouchers(person.getId()).size());
        personResponse.setPurchases(voucherService.getBuyVouchers(person.getId()).size());
        return personResponse;
    }

    public List<PersonResponse> convertEntityListToResponseList(List<Person> persons){
        List<PersonResponse> personResponses = new ArrayList<>();
        for(Person person:persons){
            personResponses.add(convertEntityToResponse(person));
        }
        return personResponses;
    }
}
