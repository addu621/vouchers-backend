package com.example.server.controllers;

import com.example.server.dto.response.WalletResponse;
import com.example.server.dto.transformer.WalletTransformer;
import com.example.server.entities.Person;
import com.example.server.entities.Wallet;
import com.example.server.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class WalletController {

    private final WalletTransformer walletTransformer;
    private final WalletService walletService;

    @GetMapping("/wallet")
    public WalletResponse getWallet(HttpServletRequest request){
        Person personDetails = (Person) request.getAttribute("person");
        Wallet wallet = this.walletService.getWalletById(personDetails.getId());
        return walletTransformer.convertEntityToResponse(wallet);
    }

    @PostMapping("/wallet/addMoney/{amount}")
    public WalletResponse addMoneyToWallet(HttpServletRequest request,@PathVariable BigDecimal amount){
        Person personDetails = (Person) request.getAttribute("person");
        Wallet wallet = this.walletService.addMoneyToWallet(personDetails.getId(),amount);
        return walletTransformer.convertEntityToResponse(wallet);
    }
}
