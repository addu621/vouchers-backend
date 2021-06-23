package com.example.server.controllers;

import com.example.server.dto.request.JwtTokenRequest;
import com.example.server.dto.request.VoucherRequest;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.dto.transformer.VoucherTransformer;
import com.example.server.entities.*;
import com.example.server.model.JwtUtil;
import com.example.server.services.CompanyService;
import com.example.server.services.VoucherService;
import io.jsonwebtoken.Jwt;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class VoucherController {

    private final VoucherService voucherService;
    private final VoucherTransformer voucherTransformer;
    private final CompanyService companyService;
    private JwtUtil jwtUtil;

    @PostMapping("/search-voucher")
    public List<Voucher> searchVoucher(@RequestBody Map<String, String> req) {
        String searchInput = req.get("input");
        List<Voucher> searchResult =  voucherService.searchVoucher(searchInput);
        return searchResult;
    }

    @PostMapping(value = "/vouchers/new")
    public VoucherResponse postVoucher(@RequestBody VoucherRequest voucherRequest) {
        Voucher voucher = voucherTransformer.convertRequestToEntity(voucherRequest);
        voucher = voucherService.saveVoucher(voucher);
        VoucherResponse voucherResponse = getVoucherResponse(voucher);
        return voucherResponse;
    }

    @GetMapping(value = "/vouchers/{id}")
    public VoucherResponse getVoucherById(@PathVariable Long id) {
        Voucher voucher = this.voucherService.getVoucherById(id);
        VoucherResponse voucherResponse = getVoucherResponse(voucher);
        return voucherResponse;
    }

    @GetMapping(value = "/vouchers")
    public List<VoucherResponse> getAllVouchers() {
        List<Voucher> vouchers = this.voucherService.getAllVouchers();
        List<VoucherResponse> voucherResponses = new ArrayList<VoucherResponse>();
        vouchers.forEach((Voucher v) -> {
            VoucherResponse voucherResponse = getVoucherResponse(v);
            voucherResponses.add(voucherResponse);
        });
        return voucherResponses;
    }

    @GetMapping(value = "/vouchers/unverified")
    public List<VoucherResponse> getAllUnverifiedVouchers() {
        List<Voucher> vouchers = this.voucherService.getAllUnverifiedVouchers();
        List<VoucherResponse> voucherResponses = new ArrayList<VoucherResponse>();
        vouchers.forEach((Voucher v) -> {
            VoucherResponse voucherResponse = getVoucherResponse(v);
            voucherResponses.add(voucherResponse);
        });
        return voucherResponses;
    }

    @GetMapping("/getVoucherCategories")
    public List<VoucherCategory> getVoucherCategories() {
        return voucherService.getAllVoucherCategory();
    }

    @GetMapping("/getVoucherCompanies")
    public List<VoucherCompany> getVoucherCompanies() {
        return voucherService.getAllVoucherCompany();
    }

    @GetMapping("/getVoucherTypes")
    public List<VoucherType> getVoucherTypes() {
        return voucherService.getAllVoucherType();
    }

    @PostMapping("/addCompany")
    public String addCompany(@RequestBody String company) {
        return voucherService.addCompany(company);
    }

    @PutMapping("/vouchers/acceptVoucher/{voucherId}")
    public String acceptVoucher(@PathVariable Long voucherId){
        return voucherService.acceptVoucher(voucherId);
    }

    @PutMapping("/vouchers/rejectVoucher/{voucherId}")
    public String rejectVoucher(@PathVariable Long voucherId){
        return voucherService.rejectVoucher(voucherId);
    }

    @GetMapping("/users/{userId}/buyVouchers")
    public List<VoucherResponse> getBuyVouchers(@PathVariable Long userId){
        List<Voucher> vouchers = voucherService.getBuyVouchers(userId);
        List<VoucherResponse> voucherResponses = new ArrayList<>();
        vouchers.forEach((Voucher voucher) -> {
            VoucherResponse voucherResponse = getVoucherResponse(voucher);
            voucherResponses.add(voucherResponse);
        });
        return voucherResponses;
    }

    @GetMapping("/users/{userId}/sellVouchers")
    public List<VoucherResponse> getSellVouchers(@PathVariable Long userId){
        List<Voucher> vouchers = voucherService.getSellVouchers(userId);
        List<VoucherResponse> voucherResponses = new ArrayList<>();
        vouchers.forEach((Voucher voucher) -> {
            VoucherResponse voucherResponse = getVoucherResponse(voucher);
            voucherResponses.add(voucherResponse);
        });
        return voucherResponses;
    }

    public VoucherResponse getVoucherResponse(Voucher voucher){
        VoucherCompany voucherCompany = voucher.getCompanyId()!=null?this.companyService.getCompanyById(voucher.getCompanyId()):null;
        VoucherResponse voucherResponse = voucherTransformer.convertEntityToResponse(voucher);
        if(voucherCompany!=null){
            voucherResponse = voucherTransformer.convertEntityToResponse(voucher,voucherCompany);
        }
        return voucherResponse;
    }
}
