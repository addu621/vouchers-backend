package com.example.server.controllers;

<<<<<<< HEAD
import com.example.server.dto.request.VoucherRequest;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.dto.transformer.VoucherTransformer;
import com.example.server.entities.Voucher;
import com.example.server.services.VoucherService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;
    private final VoucherTransformer voucherTransformer;

    @PostMapping(value = "/vouchers/new")
    public VoucherResponse postVoucher(@RequestBody VoucherRequest voucherRequest) {
        Voucher voucher = voucherTransformer.convertRequestToEntity(voucherRequest);
        voucher = voucherService.saveVoucher(voucher);
        VoucherResponse voucherResponse = voucherTransformer.convertEntityToResponse(voucher);
        return voucherResponse;
    }

    @PostMapping("/search-voucher")
    public List<Voucher> searchVoucher(@RequestBody Map<String, String> req) {
        String searchInput = req.get("input");
        List<Voucher> searchResult =  voucherService.searchVoucher(searchInput);
        return searchResult;
    }

    @GetMapping(value = "/vouchers/{id}")
    public VoucherResponse getVoucherById(@PathVariable Long id) {
        Voucher voucher = this.voucherService.getVoucherById(id);
        VoucherResponse voucherResponse = voucherTransformer.convertEntityToResponse(voucher);
        return voucherResponse;
    }

    @GetMapping(value = "/vouchers")
    public List<VoucherResponse> getAllVouchers() {
        List<Voucher> voucherList = this.voucherService.getAllVouchers();
        return voucherTransformer.convertEntityListToResponseList(voucherList);
    }
}
