package com.example.server.repositories;

import com.example.server.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonRepo extends CrudRepository<Person,Long> {
    public Person findByEmail(String email);


}

