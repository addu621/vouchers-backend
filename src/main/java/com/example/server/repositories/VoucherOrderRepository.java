package com.example.server.repositories;

import com.example.server.entities.VoucherDeal;
import com.example.server.entities.VoucherOrder;
import com.example.server.enums.DealStatus;
import com.example.server.enums.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherOrderRepository extends CrudRepository<VoucherOrder,Long> {
    List<VoucherOrder> findByBuyerIdAndOrderStatus(Long buyerId, OrderStatus orderStatus);
    List<VoucherOrder> findByOrderStatus(OrderStatus orderStatus);
    List<VoucherOrder> findByOrderStatusNot(OrderStatus orderStatus);
    List<VoucherOrder> findByTransactionId(String transactionId);
}
