package com.example.server.repositories;

import com.example.server.dto.response.SellerRatingResponse;
import com.example.server.dto.response.VoucherResponse;
import com.example.server.entities.SellerRating;
import com.example.server.entities.Voucher;
import com.example.server.enums.VoucherVerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Long> {
    @Query("select v from Voucher v where lower(v.title) like lower(concat('%', :search, '%')) " +
            "or lower(v.description) like lower(concat('%', :search, '%'))")
    List<Voucher> searchVoucher(@Param("search") String search);

    @Query("Select r.sellerId From SellerRating AS r Group by r.sellerId HAVING (AVG(r.stars))>2.0")
    List<?> ratings();

    @Query("SELECT v from Voucher v where v.id = v.id AND (:companies is null or v.companyId in :companies) AND (:categories is null or v.categoryId in :categories)  AND (:rating is null or v.sellerId in (Select r.sellerId From SellerRating AS r Group by r.sellerId HAVING (AVG(r.stars))>=:rating))")
    List<Voucher> filterCoupons(@Param("categories") List<Long>categories,@Param("companies")List<Long>companies,@Param("rating") Double averageRating);

    List<Voucher> findByVerificationStatus(VoucherVerificationStatus voucherVerificationStatus);

    List<Voucher> findBySellerId(Long sellerId);
}
