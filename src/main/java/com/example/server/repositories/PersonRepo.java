package com.example.server.repositories;

import com.example.server.dto.response.SellerRatingResponse;
import com.example.server.entities.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PersonRepo extends CrudRepository<Person,Long> {
    public Person findByEmail(String email);
    @Query("Select new com.example.server.dto.response.SellerRatingResponse(r.sellerId,AVG(r.stars)) "+"From SellerRating AS r Group by r.sellerId")
    List<SellerRatingResponse> ratings();

}

