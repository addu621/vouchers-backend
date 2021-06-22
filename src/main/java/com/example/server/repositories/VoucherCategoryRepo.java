package com.example.server.repositories;

import com.example.server.entities.VoucherCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherCategoryRepo extends JpaRepository<VoucherCategory, String> {

}
