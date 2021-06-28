package com.example.server.services;

import com.example.server.entities.Wallet;
import com.example.server.enums.TransactionType;
import com.example.server.repositories.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Transactional
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionService transactionService;

    public Wallet addCoinsToWallet(long walletId,int coins){
        Wallet wallet = walletRepository.findById(walletId).get();
        if(wallet==null){
            return null;
        }
        int coins1 = wallet.getCoins();
        wallet.setCoins(coins1+coins);
        return walletRepository.save(wallet);
    }

    public Wallet getWalletById(long walletId){
        return this.walletRepository.findById(walletId).get();
    }
}
