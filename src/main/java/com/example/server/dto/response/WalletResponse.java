package com.example.server.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WalletResponse {
    private Long id;
    private BigDecimal balance;
    private List<TransactionResponse> transactions;
}
