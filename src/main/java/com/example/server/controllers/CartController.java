package com.example.server.controllers;

import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.dto.transformer.VoucherTransformer;
import com.example.server.entities.Person;
import com.example.server.entities.Voucher;
import com.example.server.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;
    private final VoucherTransformer voucherTransformer;

    @PostMapping("/cart/addItem/{voucherId}")
    public GenericResponse addItemToCart(HttpServletRequest request,@PathVariable long voucherId){
        Person personDetails = (Person) request.getAttribute("person");
        GenericResponse genericResponse = new GenericResponse();
        boolean isSuccess = cartService.addItemToCart(personDetails.getId(),voucherId);
        if(isSuccess){
            genericResponse.setMessage("Item Added Successfully to cart");
            genericResponse.setStatus(201);
        }
        return genericResponse;
    }

    @GetMapping("/cart/items")
    public List<VoucherResponse> getVouchersByCartId(HttpServletRequest request){
        Person personDetails = (Person) request.getAttribute("person");
        List<Voucher> vouchers = cartService.getVouchersByCartId(personDetails.getId());
        return this.voucherTransformer.convertEntityListToResponseList(vouchers);
    }

    @DeleteMapping("/cart/removeItem/{voucherId}")
    public GenericResponse deleteItemFromCart(HttpServletRequest request,@PathVariable long voucherId){
        Person personDetails = (Person) request.getAttribute("person");
        GenericResponse genericResponse = new GenericResponse();
        boolean isSuccess = cartService.removeItemFromCart(personDetails.getId(),voucherId);
        if(isSuccess){
            genericResponse.setMessage("Item Deleted From Cart");
            genericResponse.setStatus(200);
        }
        return genericResponse;
    }

    @GetMapping("/cart/checkout/{transactionId}/{noOfCoinsReedemed}")
    public List<VoucherResponse> checkoutCart(HttpServletRequest request,@PathVariable String transactionId, @PathVariable int noOfCoinsReedemed){
        Person personDetails = (Person) request.getAttribute("person");
        List<Voucher> vouchers = cartService.checkOutCart(personDetails.getId(),transactionId,noOfCoinsReedemed);
        return this.voucherTransformer.convertEntityListToResponseList(vouchers);
    }
}
