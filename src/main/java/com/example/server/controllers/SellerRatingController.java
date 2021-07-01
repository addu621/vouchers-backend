package com.example.server.controllers;

import com.example.server.dto.request.SellerRatingRequest;
import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.response.SellerRatingResponse;
import com.example.server.entities.Person;
import com.example.server.entities.SellerRating;
import com.example.server.services.SellerRatingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class SellerRatingController {

    @Autowired
    SellerRatingService sellerRatingService;

    @GetMapping("/rating/get/{sellerId}")
    public SellerRatingResponse getSellerRating(@PathVariable Long sellerId){
        return sellerRatingService.getSellerRating(sellerId);
    }

    /*
    Description: buyer can rate seller using this route
    */
    @PostMapping("/rating/post")
    public GenericResponse rateSeller(HttpServletRequest request, @RequestBody SellerRatingRequest sellerRatingRequest){
        GenericResponse genericResponse = new GenericResponse();

        Person personDetails = (Person) request.getAttribute("person");
        Long buyerId = personDetails.getId();
        Long sellerId = sellerRatingRequest.getSellerId();
        Long voucherId = sellerRatingRequest.getVoucherId();
        int stars = sellerRatingRequest.getStars();
        String comment = sellerRatingRequest.getComment();

        if(buyerId==sellerId){
            genericResponse.setMessage("You cannot rate yourself!!");
            return genericResponse;
        }

        SellerRating result = sellerRatingService.rateSeller(buyerId,sellerId,voucherId,stars,comment);
        if(result == null){
                genericResponse.setMessage("You have given rating already!");
        }
        else{
            genericResponse.setMessage("You have successfully rated the seller");
            genericResponse.setStatus(200);
        }
        return genericResponse;
    }
}
