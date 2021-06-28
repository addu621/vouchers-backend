package com.example.server.repositories;

import com.example.server.entities.VoucherOrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherOrderDetailRepository extends CrudRepository<VoucherOrderDetail,Long> {

    List<VoucherOrderDetail> findByOrderId(Long orderId);
}
