package com.example.server.services;

import com.example.server.dto.response.SellerRatingResponse;
import com.example.server.entities.SellerRating;
import com.example.server.repositories.SellerRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SellerRatingService {

    @Autowired
    SellerRatingRepository sellerRatingRepository;

    @Autowired
    Utility utility;

    public SellerRatingResponse getSellerRating(Long sellerId){
        SellerRatingResponse sellerRatingResponse = new SellerRatingResponse(sellerId,0.0);
        SellerRatingResponse result = sellerRatingRepository.ratingOfSeller(sellerId);
        if(result==null){
            return sellerRatingResponse;
        }
        return result;
    }

    /*
    Description: buyer can rate seller for a given voucher
    -> if buyerId sellerId and voucherId are not there in db
        ->  create new rating
    -> else return error
    */
    public SellerRating rateSeller(Long buyerId, Long sellerId, Long voucherId, int stars, String comment ){
        List<SellerRating> sellerRatingList = sellerRatingRepository.findByBuyerIdAndSellerIdAndVoucherId(buyerId,sellerId,voucherId);

        if(sellerRatingList.size()!=0){
            // rating already exists for given sellerId,buyerId,voucherId
            return null;
        }
        SellerRating sellerRating = new SellerRating();
        sellerRating.setSellerId(sellerId);
        sellerRating.setBuyerId(buyerId);
        sellerRating.setComment(comment);
        sellerRating.setStars(stars);
        sellerRating.setVoucherId(voucherId);
        sellerRatingRepository.save(sellerRating);
        return sellerRating;
    }



}
