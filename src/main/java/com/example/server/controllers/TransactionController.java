package com.example.server.controllers;

import com.example.server.dto.request.TransactionGraphRequest;
import com.example.server.dto.response.PieChartResponse;
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
    @PostMapping("/vouchers/graph/month")
    public List<?> graphMonth(@RequestBody TransactionGraphRequest transactionGraphRequest) throws Exception{
        return transactionService.generateGraphByMonth(transactionGraphRequest);
    }
    @PostMapping("/vouchers/graph/year")
    public List<?> graphYear(@RequestBody TransactionGraphRequest transactionGraphRequest) throws Exception{
        return transactionService.generateGraphByYear(transactionGraphRequest);
    }

    @PostMapping("/vouchers/pie")
    public PieChartResponse createPie(@RequestBody TransactionGraphRequest transactionGraphRequest)throws Exception{
        return transactionService.createPie(transactionGraphRequest);
    }
    @PostMapping("/vouchers/pie/month")
    public PieChartResponse createPieByMonth(@RequestBody TransactionGraphRequest transactionGraphRequest)throws Exception{
        return transactionService.createPieByMonth(transactionGraphRequest);
    }
    @PostMapping("/vouchers/pie/year")
    public PieChartResponse createPieByYear(@RequestBody TransactionGraphRequest transactionGraphRequest)throws Exception{
        return transactionService.createPieByYear(transactionGraphRequest);
    }
}
