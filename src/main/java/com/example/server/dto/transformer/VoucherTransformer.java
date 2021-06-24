package com.example.server.dto.transformer;

import com.example.server.dto.request.VoucherRequest;
import com.example.server.dto.response.PersonResponse;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.Person;
import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherCompany;
import com.example.server.enums.VoucherVerificationStatus;
import com.example.server.services.CompanyService;
import com.example.server.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@AllArgsConstructor
public class VoucherTransformer {

    private final CompanyService companyService;
    private final PersonService personService;

    public Voucher convertRequestToEntity(VoucherRequest voucherRequest){
        Voucher voucher = new Voucher();
        copyProperties(voucherRequest, voucher);
        voucher.setCreatedOn(new Date().toString());
        voucher.setVerificationStatus(VoucherVerificationStatus.PENDING);
        return voucher;
    }

    public VoucherResponse convertEntityToResponse(Voucher voucher){
        Person seller = voucher.getSellerId() != null ? this.personService.findById(voucher.getSellerId()):null;
        VoucherCompany voucherCompany = voucher.getCompanyId() != null? this.companyService.getCompanyById(voucher.getCompanyId()):null;

        VoucherResponse voucherResponse = new VoucherResponse();
        copyProperties(voucher, voucherResponse);

        if(seller!=null){
            PersonResponse personResponse = new PersonResponse();
            copyProperties(seller,personResponse);
            voucherResponse.setSeller(personResponse);
        }
        if(voucherCompany!=null){
            voucherResponse.setCompanyImgUrl(voucherCompany.getImageUrl());
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
