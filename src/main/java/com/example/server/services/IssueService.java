package com.example.server.services;

import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Issue;
import com.example.server.entities.VoucherOrder;
import com.example.server.entities.VoucherOrderDetail;
import com.example.server.repositories.IssueRepo;
import com.example.server.repositories.VoucherOrderDetailRepository;
import com.example.server.repositories.VoucherOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueService {

    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private VoucherOrderDetailRepository voucherOrderDetailRepository;

    @Autowired
    private VoucherOrderRepository voucherOrderRepository;

    @Autowired
    private Utility utility;

    @Autowired
    private ChatService chatService;

    public GenericResponse submitIssue(Long orderItemId,String comment) {

        GenericResponse genericResponse = new GenericResponse();

        if(orderItemId==null) {
            genericResponse.setStatus(404);
            genericResponse.setMessage("voucherId cannot be empty");
            return genericResponse;
        }
        if(comment==null) {
            genericResponse.setStatus(404);
            genericResponse.setMessage("Comment cannot be empty");
            return genericResponse;
        }

        Issue issue = new Issue();
        issue.setComment(comment);
        issue.setOrderItemId(orderItemId);
        issueRepo.save(issue);

        genericResponse.setMessage("Issue submitted successfully!!!");
        genericResponse.setStatus(200);
        return genericResponse;
    }

    public List<Issue> getIssues() {
        List<Issue> issues = issueRepo.findByIsClosedOrderByCreatedDate(false);
        return issues;
    }

    public List<Issue> getIssuesOfUser(long userId){
        return issueRepo.findAll().stream().filter((Issue issue)->{
            VoucherOrderDetail voucherOrderDetail = this.voucherOrderDetailRepository.findById(issue.getIssueId()).get();
            VoucherOrder voucherOrder = this.voucherOrderRepository.findById(voucherOrderDetail.getOrderId()).get();
            return voucherOrder.getBuyerId()==userId;
        }).collect(Collectors.toList());
    }

    public GenericResponse notificationRead(Long issueId) {
        GenericResponse genericResponse = new GenericResponse();
        Issue issue = issueRepo.findByIssueId(issueId);
        if(issue==null){
            genericResponse.setStatus(404);
            genericResponse.setMessage("Issue not found!!!");
            return genericResponse;
        }
        issue.setIsRead(true);
        issueRepo.save(issue);
        genericResponse.setMessage("Issue Read!!!");
        genericResponse.setStatus(200);
        return genericResponse;
    }

    public GenericResponse issueClosed(Long issueId) throws UnsupportedEncodingException, MessagingException {
        GenericResponse genericResponse = new GenericResponse();
        Issue issue = issueRepo.findByIssueId(issueId);
        if(issue==null){
            genericResponse.setStatus(404);
            genericResponse.setMessage("Issue not found!!!");
            return genericResponse;
        }
        issue.setIsClosed(true);
        issueRepo.save(issue);
        genericResponse.setMessage("Issue Closed!!!");
        genericResponse.setStatus(200);
        utility.issueClosedMail(issue);
        return genericResponse;
    }

    public GenericResponse issueDeleted(Long issueId) {
        GenericResponse genericResponse = new GenericResponse();
        Issue issue = issueRepo.findByIssueId(issueId);
        if(issue==null){
            genericResponse.setStatus(404);
            genericResponse.setMessage("Issue not found!!!");
            return genericResponse;
        }
        issueRepo.delete(issue);
        genericResponse.setMessage("Issue Deleted!!!");
        genericResponse.setStatus(200);
        return genericResponse;
    }
}
