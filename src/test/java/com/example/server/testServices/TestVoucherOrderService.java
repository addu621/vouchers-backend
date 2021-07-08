package com.example.server.testServices;

import com.example.server.entities.Voucher;
import com.example.server.entities.VoucherOrder;
import com.example.server.entities.VoucherOrderDetail;
import com.example.server.enums.OrderStatus;
import com.example.server.repositories.VoucherOrderDetailRepository;
import com.example.server.repositories.VoucherOrderRepository;
import com.example.server.services.VoucherOrderService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestVoucherOrderService {

    @Mock
    private VoucherOrderDetailRepository voucherOrderDetailRepository;

    @Mock
    private VoucherOrderRepository voucherOrderRepository;

    @Autowired
    private VoucherOrderService voucherOrderService;

    @Test
    public void testCreateOrder(){
        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setBuyerId(1L);
        Mockito.when(voucherOrderRepository.save(voucherOrder)).thenReturn(voucherOrder);
        assertEquals(1L,voucherOrderService.createOrder(1L).getBuyerId());
    }

    @Test
    public void testAddOrderItem(){
        VoucherOrderDetail voucherOrderDetail = new VoucherOrderDetail();
        voucherOrderDetail.setVoucherId(5L);
        Mockito.when(voucherOrderDetailRepository.save(voucherOrderDetail)).thenReturn(voucherOrderDetail);
        assertEquals(5L,voucherOrderService.addOrderItem(1L,5L).getVoucherId());
    }

    @Test
    public void getBuyOrders(){
        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setId(1L);
        voucherOrder.setBuyerId(5L);

        List<VoucherOrder> voucherOrderList = new ArrayList<>();
        voucherOrderList.add(voucherOrder);

        VoucherOrderDetail voucherOrderDetail1 = new VoucherOrderDetail();
        voucherOrderDetail1.setOrderId(1L);
        voucherOrderDetail1.setVoucherId(2L);

        VoucherOrderDetail voucherOrderDetail2 = new VoucherOrderDetail();
        voucherOrderDetail2.setOrderId(1L);

        VoucherOrderDetail voucherOrderDetail3 = new VoucherOrderDetail();
        voucherOrderDetail3.setOrderId(1L);

        List<VoucherOrderDetail> voucherOrderDetailList = new ArrayList<>();
        voucherOrderDetailList.add(voucherOrderDetail1);
        voucherOrderDetailList.add(voucherOrderDetail2);
        voucherOrderDetailList.add(voucherOrderDetail3);

        Mockito.when(voucherOrderRepository.findByBuyerIdAndOrderStatus(5L, OrderStatus.SUCCESS)).thenReturn(voucherOrderList);
        Mockito.when(voucherOrderDetailRepository.findByOrderId(1L)).thenReturn(voucherOrderDetailList);

        assertEquals(3,voucherOrderService.getBuyOrders(5L).size());
        assertEquals(2,voucherOrderService.getBuyOrders(5L).get(0).getVoucherId());
    }
}
