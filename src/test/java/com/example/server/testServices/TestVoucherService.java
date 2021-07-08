package com.example.server.testServices;

import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherOrderDetail;
import com.example.server.repositories.VoucherOrderDetailRepository;
import com.example.server.repositories.VoucherRepository;
import com.example.server.services.VoucherOrderService;
import com.example.server.services.VoucherService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestVoucherService {

    @Mock
    private VoucherRepository voucherRepository;

    @InjectMocks
    private VoucherService voucherService;

    @InjectMocks
    private VoucherOrderService voucherOrderService;

    @Mock
    private VoucherOrderDetailRepository voucherOrderDetailRepository;

    @Test
    public void testSaveVoucher(){
        Voucher voucher = new Voucher();
        voucher.setId(1L);
        voucher.setSellingPrice(new BigDecimal(100));
        voucher.setVoucherValue(new BigDecimal(500));
        voucher.setTitle("Test Voucher");
        voucher.setDescription("Test Voucher Description");
        voucher.setCompanyId(1L);
        voucher.setVoucherCode("test123");

        List<Voucher> sampleSavedVouchers = new ArrayList<>();
        sampleSavedVouchers.add(voucher);

        Mockito.when(voucherRepository.save(voucher)).thenReturn(voucher);

        //negative test case
        Mockito.when(voucherRepository.findByCompanyIdAndVoucherCode(voucher.getCompanyId(), voucher.getVoucherCode())).thenReturn(sampleSavedVouchers);
        assertEquals(409,voucherService.saveVoucher(voucher).getStatus());

        //positive test case
        Mockito.when(voucherRepository.findByCompanyIdAndVoucherCode(voucher.getCompanyId(), voucher.getVoucherCode())).thenReturn(new ArrayList<>());
        assertEquals(201,voucherService.saveVoucher(voucher).getStatus());
    }

    @Test
    public void testIsVoucherSold(){
        VoucherOrderDetail voucherOrderDetail = new VoucherOrderDetail();
        voucherOrderDetail.setVoucherId(1L);

        List<VoucherOrderDetail> soldVoucherOrders = new ArrayList<>();
        soldVoucherOrders.add(voucherOrderDetail);

        //negative test case
        Mockito.when(voucherOrderDetailRepository.findAll()).thenReturn(soldVoucherOrders);
        assertEquals(true,voucherService.isVoucherSold(1L));

        //positive test case
        Mockito.when(voucherOrderDetailRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(false,voucherService.isVoucherSold(1L));
    }

    @Test
    public void testGetAllVouchers(){
        Voucher voucher = new Voucher();
        voucher.setId(1L);
        voucher.setSellingPrice(new BigDecimal(100));
        voucher.setVoucherValue(new BigDecimal(500));
        voucher.setTitle("Test Voucher");
        voucher.setDescription("Test Voucher Description");
        voucher.setCompanyId(1L);
        voucher.setVoucherCode("test123");

        List<Voucher> sampleSavedVouchers = new ArrayList<>();
        sampleSavedVouchers.add(voucher);

        Mockito.when(voucherRepository.findAll()).thenReturn(sampleSavedVouchers);

        VoucherOrderDetail voucherOrderDetail = new VoucherOrderDetail();
        voucherOrderDetail.setVoucherId(1L);

        List<VoucherOrderDetail> soldVoucherOrders = new ArrayList<>();
        soldVoucherOrders.add(voucherOrderDetail);

        //negative case
        Mockito.when(voucherOrderDetailRepository.findAll()).thenReturn(soldVoucherOrders);
        assertEquals(0,voucherService.getAllVouchers().size());

        //positive case
        Mockito.when(voucherOrderDetailRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(1,voucherService.getAllVouchers().size());
    }
}
