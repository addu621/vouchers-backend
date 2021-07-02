package com.example.server.dto.transformer;

import com.example.server.dto.response.IssueResponse;
import com.example.server.dto.response.OrderResponse;
import com.example.server.entities.Issue;
import com.example.server.entities.VoucherOrder;
import com.example.server.entities.VoucherOrderDetail;
import com.example.server.repositories.VoucherOrderDetailRepository;
import com.example.server.repositories.VoucherOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
@AllArgsConstructor
public class IssueTransformer {

    private final VoucherOrderDetailRepository voucherOrderDetailRepository;
    private final VoucherOrderRepository voucherOrderRepository;
    private final OrderTransformer orderTransformer;

    public IssueResponse convertEntityToResponse(Issue issue){
        IssueResponse issueResponse = new IssueResponse();
        copyProperties(issue,issueResponse);
        VoucherOrderDetail voucherOrderDetail = this.voucherOrderDetailRepository.findById(issue.getOrderItemId()).get();
        VoucherOrder voucherOrder = this.voucherOrderRepository.findById(voucherOrderDetail.getOrderId()).get();
        issueResponse.setUserId(voucherOrder.getBuyerId());
        OrderResponse orderResponse = orderTransformer.convertEntityToResponse(voucherOrderDetail);
        issueResponse.setOrder(orderResponse);
        System.out.println(issueResponse);
        return issueResponse;
    }

    public List<IssueResponse> convertEntityListToResponseList(List<Issue> issues){
        List<IssueResponse> issueResponses = new ArrayList<>();
        for(Issue issue:issues){
            issueResponses.add(convertEntityToResponse(issue));
        }
        return issueResponses;
    }
}
