package com.example.server.repositories;

import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.Voucher;
import com.example.server.enums.VoucherVerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Long> {
    @Query("select v from Voucher v where lower(v.title) like lower(concat('%', :search, '%')) " +
            "or lower(v.description) like lower(concat('%', :search, '%'))")
    List<Voucher> searchVoucher(@Param("search") String search);

    @Query("select v from Voucher v where v.companyId in (:companies) and v.categoryId in (:categories)")
    List<Voucher> filterCoupons(@Param("categories") List<Long>categories,@Param("companies")List<Long>companies);

    List<Voucher> findByVerificationStatus(VoucherVerificationStatus voucherVerificationStatus);

    List<Voucher> findBySellerId(Long sellerId);
}
