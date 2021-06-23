package com.example.server.controllers;

import com.example.server.dto.request.FilterRequest;
import com.example.server.dto.request.VoucherRequest;
import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.dto.transformer.VoucherTransformer;
import com.example.server.entities.*;
import com.example.server.services.CompanyService;
import com.example.server.services.PersonService;
import com.example.server.services.VoucherService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final PersonService personService;

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

    @GetMapping(value = "/vouchers/verified")
    public List<VoucherResponse> getAllVerifiedVouchers() {
        List<Voucher> vouchers = this.voucherService.getAllVerifiedVouchers();
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

    /*@PostMapping("/filter")
    public List<Voucher> filter(@RequestBody FilterRequest input) {
        List<Voucher> result = voucherService.filterVouchers(input);
        return result;
    }*/

    @PutMapping("/vouchers/acceptVoucher/{voucherId}")
    public String acceptVoucher(@PathVariable Long voucherId){
        return voucherService.acceptVoucher(voucherId);
    }

    @PutMapping("/vouchers/rejectVoucher/{voucherId}")
    public String rejectVoucher(@PathVariable Long voucherId){
        return voucherService.rejectVoucher(voucherId);
    }

    @GetMapping("/users/buyVouchers")
    public List<VoucherResponse> getBuyVouchers(HttpServletRequest request){
        Person personDetails = (Person) request.getAttribute("person");
        List<Voucher> vouchers = voucherService.getBuyVouchers(personDetails.getId());
        List<VoucherResponse> voucherResponses = new ArrayList<>();
        vouchers.forEach((Voucher voucher) -> {
            VoucherResponse voucherResponse = getVoucherResponse(voucher);
            voucherResponses.add(voucherResponse);
        });
        return voucherResponses;
    }

    @GetMapping("/users/sellVouchers")
    public List<VoucherResponse> getSellVouchers(HttpServletRequest request){
        Person personDetails = (Person) request.getAttribute("person");
        List<Voucher> vouchers = voucherService.getSellVouchers(personDetails.getId());
        List<VoucherResponse> voucherResponses = new ArrayList<>();
        vouchers.forEach((Voucher voucher) -> {
            VoucherResponse voucherResponse = getVoucherResponse(voucher);
            voucherResponses.add(voucherResponse);
        });
        return voucherResponses;
    }

    public VoucherResponse getVoucherResponse(Voucher voucher){
        VoucherResponse voucherResponse = voucherTransformer.convertEntityToResponse(voucher);
        Person seller = voucher.getSellerId() != null ? this.personService.findById(voucher.getSellerId()):null;
        VoucherCompany voucherCompany = voucher.getCompanyId() != null? this.companyService.getCompanyById(voucher.getCompanyId()):null;
        voucherResponse = voucherTransformer.convertEntityToResponse(voucher,seller,voucherCompany);
        return voucherResponse;
    }
}
