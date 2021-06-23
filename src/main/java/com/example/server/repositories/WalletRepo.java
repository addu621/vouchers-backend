package com.example.server.repositories;

import com.example.server.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<Wallet,String> {

}
