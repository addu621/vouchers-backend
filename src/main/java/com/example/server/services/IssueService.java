package com.example.server.services;

import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.Issue;
import com.example.server.repositories.IssueRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    @Autowired
    private IssueRepo issueRepo;

    public GenericResponse submitIssue(String transactionId,Long voucherId,String comment) {

        GenericResponse genericResponse = new GenericResponse();

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

    public List<Issue> getIssues() {
        List<Issue> issues = issueRepo.findByIsClosedOrderByCreatedDate(false);
        return issues;
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

    public GenericResponse issueClosed(Long issueId) {
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
