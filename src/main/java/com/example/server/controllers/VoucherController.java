package com.example.server.controllers;

import com.example.server.dto.request.FilterRequest;
import com.example.server.dto.request.VoucherRequest;
import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.response.SellerRatingResponse;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.dto.transformer.VoucherTransformer;
import com.example.server.entities.*;
import com.example.server.services.CompanyService;
import com.example.server.services.PersonService;
import com.example.server.services.VoucherOrderService;
import com.example.server.services.VoucherService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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
    private final VoucherOrderService voucherOrderService;

    @PostMapping("/search-voucher")
    public List<Voucher> searchVoucher(@RequestBody Map<String, String> req) {
        String searchInput = req.get("input");
        List<Voucher> searchResult =  voucherService.searchVoucher(searchInput);
        return searchResult;
    }

    @PostMapping(value = "/vouchers/new")
    public VoucherResponse postVoucher(HttpServletRequest request,@RequestBody VoucherRequest voucherRequest) throws ParseException {
        Person personDetails = (Person) request.getAttribute("person");
        voucherRequest.setSellerId(personDetails.getId());
        Voucher voucher = voucherTransformer.convertRequestToEntity(voucherRequest);
        voucher = voucherService.saveVoucher(voucher);
        VoucherResponse voucherResponse = this.voucherTransformer.convertEntityToResponse(voucher);
        return voucherResponse;
    }

    @GetMapping(value = "/vouchers/{id}")
    public VoucherResponse getVoucherById(@PathVariable Long id) {
        Voucher voucher = this.voucherService.getVoucherById(id);
        VoucherResponse voucherResponse = this.voucherTransformer.convertEntityToResponse(voucher);
        return voucherResponse;
    }

    @GetMapping(value = "/vouchers")
    public List<VoucherResponse> getAllVouchers() {
        List<Voucher> vouchers = this.voucherService.getAllVouchers();
        return this.voucherTransformer.convertEntityListToResponseList(vouchers);
    }

    @GetMapping(value = "/vouchers/verified")
    public List<VoucherResponse> getAllVerifiedVouchers() {
        List<Voucher> vouchers = this.voucherService.getAllVerifiedVouchers();
        return this.voucherTransformer.convertEntityListToResponseList(vouchers);
    }

    @GetMapping(value = "/vouchers/unverified")
    public List<VoucherResponse> getAllUnverifiedVouchers() {
        List<Voucher> vouchers = this.voucherService.getAllUnverifiedVouchers();
        return this.voucherTransformer.convertEntityListToResponseList(vouchers);
    }

    @GetMapping(value = "/user/myVouchers")
    public List<VoucherResponse> getMyVouchers(HttpServletRequest request) {
        Person personDetails = (Person) request.getAttribute("person");
        List<Voucher> vouchers = this.voucherService.getVouchersBySellerId(personDetails.getId());
        return this.voucherTransformer.convertEntityListToResponseList(vouchers);
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

    @GetMapping("/users/buyVouchers")
    public List<VoucherResponse> getBuyVouchers(HttpServletRequest request){
        Person personDetails = (Person) request.getAttribute("person");
        List<Voucher> vouchers = voucherService.getBuyVouchers(personDetails.getId());
        return this.voucherTransformer.convertEntityListToResponseList(vouchers);
    }

    @GetMapping("/users/sellVouchers")
    public List<VoucherResponse> getSellVouchers(HttpServletRequest request){
        Person personDetails = (Person) request.getAttribute("person");
        List<Voucher> vouchers = voucherService.getSellVouchers(personDetails.getId());
        return this.voucherTransformer.convertEntityListToResponseList(vouchers);
    }

    @GetMapping("/buy/voucher/{voucherId}/{transactionId}")
    public GenericResponse buyVoucher(HttpServletRequest request,@PathVariable Long voucherId, @PathVariable String transactionId){
        Person personDetails = (Person) request.getAttribute("person");
        Long buyerId = personDetails.getId();
        System.out.println("vId = "+voucherId);
        System.out.println("tId = "+transactionId);
        VoucherOrder voucherOrder = voucherOrderService.createOrder(buyerId,transactionId);
        VoucherOrderDetail voucherOrderItem = voucherOrderService.addOrderItem(voucherOrder.getId(),voucherId);
        voucherOrderService.placeOrder(voucherOrder.getId());

        GenericResponse genericResponse= new GenericResponse();
        if(voucherOrderItem!=null){
            genericResponse.setMessage("Voucher Bought Successfully");
            genericResponse.setStatus(200);
        }
        return genericResponse;
    }

    @GetMapping("/vouchers/{voucherId}/isSold")
    public boolean isVoucherSold(@PathVariable long voucherId){
        return this.voucherService.isVoucherSold(voucherId);
    }

    @PostMapping("/filter")
    public List<Voucher> filter(@RequestBody FilterRequest input) {
        List<Voucher> result = voucherService.filterVouchers(input);
        return result;
    }
}
