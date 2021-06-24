package com.example.server.repositories;

import com.example.server.entities.VoucherDeal;
import com.example.server.enums.DealStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherDealRepository extends CrudRepository<VoucherDeal,Long> {
    List<VoucherDeal> findByBuyerIdAndDealStatus(Long buyerId, DealStatus dealStatus);
    List<VoucherDeal> findByVoucherIdAndDealStatus(Long voucherId,DealStatus dealStatus);
    List<VoucherDeal> findByDealStatus(DealStatus dealStatus);
    List<VoucherDeal> findByDealStatusNot(DealStatus dealStatus);
}
