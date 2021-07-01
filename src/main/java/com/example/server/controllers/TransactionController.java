package com.example.server.controllers;

import com.example.server.dto.request.TransactionGraphRequest;
import com.example.server.dto.response.TransactionGraphResponse;
import com.example.server.dto.response.TransactionResponse;
import com.example.server.entities.Transaction;
import com.example.server.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

        @PostMapping("/vouchers/graph")
        public List<?> graph(@RequestBody TransactionGraphRequest transactionGraphRequest) throws Exception{
            return transactionService.generateGraph(transactionGraphRequest);
    }
}