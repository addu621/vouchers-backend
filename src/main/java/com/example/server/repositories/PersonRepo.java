package com.example.server.repositories;

import com.example.server.dto.response.SellerRatingResponse;
import com.example.server.entities.Person;
import com.example.server.entities.SellerRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepo extends CrudRepository<Person,Long> {
    public Person findByEmail(String email);

    List<Person> findBySsnNotNullAndSsnVerifiedFalse();

}

