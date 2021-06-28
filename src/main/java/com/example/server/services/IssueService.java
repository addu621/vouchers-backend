package com.example.server.services;

import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Issue;
import com.example.server.repositories.IssueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueService {

    @Autowired
    private IssueRepo issueRepo;

    public GenericResponse submitIssue(Long transactionId,Long voucherId,String comment) {

        GenericResponse genericResponse = new GenericResponse();

        System.out.println(transactionId);
        System.out.println(voucherId);
        System.out.println(comment);
        if(voucherId==null) {
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
        issue.setTransactionId(transactionId);
        issue.setVoucherId(voucherId);
        issueRepo.save(issue);

        genericResponse.setMessage("Issue submitted successfully!!!");
        genericResponse.setStatus(200);
        return genericResponse;
    }
}
