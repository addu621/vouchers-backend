package com.example.server.services;

import com.example.server.entities.Voucher;
import com.example.server.repositories.VoucherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepo voucherRepo;

    public List<Voucher> searchVoucher(String search){
        List<Voucher> searchResult = voucherRepo.searchVoucher(search);
        return searchResult;
    }
}
