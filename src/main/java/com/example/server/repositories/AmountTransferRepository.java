package com.example.server.repositories;

import com.example.server.entities.AmountTransfer;
import com.example.server.enums.AmountTransferStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmountTransferRepository extends CrudRepository<AmountTransfer,Long> {
    List<AmountTransfer> findByAmountTransferStatus(AmountTransferStatus amountTransferStatus);
}
