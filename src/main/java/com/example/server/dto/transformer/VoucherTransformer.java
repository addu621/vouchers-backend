package com.example.server.dto.transformer;

import com.example.server.dto.request.VoucherRequest;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.Voucher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class VoucherTransformer {

    public Voucher convertRequestToEntity(VoucherRequest voucherRequest){
        Voucher voucher = new Voucher();
        System.out.println("voucherDetails of Request:"+voucherRequest);
        copyProperties(voucherRequest, voucher);
        voucher.setCreatedOn(new Date().toString());
        System.out.println("voucherDetails:"+voucher);
        /*voucher.setNegotiable(voucherRequest.isNegotiable());
        voucher.setVoucherCode(voucherRequest.getVoucherCode());
        voucher.setVoucherValue(voucherRequest.getVoucherValue());
        voucher.setExpiryDate(voucherRequest.getExpiryDate());
        voucher.setSellingPrice(voucherRequest.getSellingPrice());
        voucher.setDescription(voucherRequest.getDescription());
        voucher.setTitle(voucherRequest.getTitle());*/
        return voucher;
    }

    public VoucherResponse convertEntityToResponse(Voucher voucher){
        VoucherResponse voucherResponse = new VoucherResponse();
        copyProperties(voucher, voucherResponse);
        /*voucherResponse.setId(voucher.getId());
        voucherResponse.setVoucherCode(voucher.getVoucherCode());
        voucherResponse.setVoucherValue(voucher.getVoucherValue());
        voucherResponse.setSellingPrice(voucher.getSellingPrice());
        voucherResponse.setVoucherValue(voucher.getVoucherValue());
        voucherResponse.setTitle(voucher.getTitle());
        voucher.setDescription(voucher.getDescription());
        voucher.setExpiryDate(voucher.getExpiryDate());*/
        return voucherResponse;
    }

    public List<VoucherResponse> convertEntityListToResponseList(List<Voucher> vouchers){
        List<VoucherResponse> voucherResponses = new ArrayList<VoucherResponse>();
        vouchers.forEach((Voucher v) -> {
            VoucherResponse voucherResponse = convertEntityToResponse(v);
            voucherResponses.add(voucherResponse);
        });
        return voucherResponses;
    }
}
