package com.example.server.dto.transformer;

import com.example.server.dto.request.VoucherRequest;
import com.example.server.dto.response.PersonResponse;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.Person;
import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherCompany;
import com.example.server.enums.VoucherVerificationStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class VoucherTransformer {

    public Voucher convertRequestToEntity(VoucherRequest voucherRequest){
        Voucher voucher = new Voucher();
        copyProperties(voucherRequest, voucher);
        voucher.setCreatedOn(new Date().toString());
        voucher.setVerificationStatus(VoucherVerificationStatus.PENDING);
        return voucher;
    }

    public VoucherResponse convertEntityToResponse(Voucher voucher){
        VoucherResponse voucherResponse = new VoucherResponse();
        copyProperties(voucher, voucherResponse);
        return voucherResponse;
    }

    public VoucherResponse convertEntityToResponse(Voucher voucher, Person seller, VoucherCompany voucherCompany){
        VoucherResponse voucherResponse = new VoucherResponse();
        copyProperties(voucher, voucherResponse);

        PersonResponse personResponse = new PersonResponse();
        if(seller!=null){
            copyProperties(seller,personResponse);
            voucherResponse.setSeller(personResponse);
        }
        if(voucherCompany!=null){
            voucherResponse.setCompanyImgUrl(voucherCompany.getImageUrl());
        }
        return voucherResponse;
    }

    public VoucherResponse convertEntityToResponse(Voucher voucher, Person seller){
        VoucherResponse voucherResponse = new VoucherResponse();
        copyProperties(voucher, voucherResponse);

        if(seller!=null){
            PersonResponse personResponse = new PersonResponse();
            copyProperties(seller,personResponse);
            voucherResponse.setSeller(personResponse);
        }

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
