package com.example.server.repositories;

import com.example.server.entities.VoucherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherTypeRepo extends JpaRepository<VoucherType,String> {

}
