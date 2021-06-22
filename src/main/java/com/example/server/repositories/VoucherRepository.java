package com.example.server.repositories;

import com.example.server.entities.Voucher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends CrudRepository<Voucher,Long> {
    @Query("select v from Voucher v where lower(v.title) like lower(concat('%', :search, '%')) " +
            "or lower(v.description) like lower(concat('%', :search, '%'))")
    List<Voucher> searchVoucher(@Param("search") String search);
}
