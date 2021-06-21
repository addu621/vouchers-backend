package com.example.server.services;

import com.example.server.entities.Voucher;
import com.example.server.repositories.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public Voucher saveVoucher(Voucher voucher){
        return voucherRepository.save(voucher);
    }

    public Voucher getVoucherById(Long id){
        return voucherRepository.findById(id).get();
    }

    public List<Voucher> getAllVouchers(){
        List<Voucher> vouchers =  (List<Voucher>) voucherRepository.findAll();
        return vouchers;
    }
}
