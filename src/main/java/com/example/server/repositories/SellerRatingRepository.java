package com.example.server.repositories;


import com.example.server.dto.response.SellerRatingResponse;
import com.example.server.entities.SellerRating;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRatingRepository extends CrudRepository<SellerRating,Long> {
    @Query("Select new com.example.server.dto.response.SellerRatingResponse(r.sellerId,AVG(r.stars)) "+"From SellerRating AS r Group by r.sellerId")
    List<SellerRatingResponse> ratings();

    @Query("Select new com.example.server.dto.response.SellerRatingResponse(r.sellerId,AVG(r.stars)) "+"From SellerRating AS r where r.sellerId = :sellerId Group by r.sellerId")
    SellerRatingResponse ratingOfSeller(@Param("sellerId") Long sellerId);

    List<SellerRating> findByBuyerIdAndSellerIdAndVoucherId(Long buyerId,Long sellerId,Long voucherId);
}
