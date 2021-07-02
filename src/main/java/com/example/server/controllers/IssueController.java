package com.example.server.controllers;

import com.example.server.dto.response.GenericResponse;
import com.example.server.dto.response.IssueResponse;
import com.example.server.dto.transformer.IssueTransformer;
import com.example.server.entities.Issue;
import com.example.server.services.IssueService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class IssueController {

    @Autowired
    public IssueService issueService;

    @Autowired
    private IssueTransformer issueTransformer;

    @PostMapping("/issue/submit")
    public GenericResponse submitIssue(@RequestBody Map<String,String> issue){
        GenericResponse genericResponse = new GenericResponse();
        System.out.println(issue);
        return issueService.submitIssue(Long.parseLong(issue.get("orderItemId")), issue.get("comment"));
    }

    @GetMapping("/getIssues")
    public List<IssueResponse> getIssues() {
        List<Issue> allIssues = issueService.getIssues();
        return issueTransformer.convertEntityListToResponseList(allIssues);
    }

    @PutMapping("/issue/{issueId}/is-read")
    public GenericResponse notificationRead(@PathVariable Long issueId) {
        return issueService.notificationRead(issueId);
    }

    @PutMapping("/issue/{issueId}/mark-closed")
    public GenericResponse issueClosed(@PathVariable Long issueId) throws UnsupportedEncodingException, MessagingException {
        return issueService.issueClosed(issueId);
    }

    @DeleteMapping("/issue/{issueId}/delete")
    public GenericResponse issueDelete(@PathVariable Long issueId) {
        return issueService.issueDeleted(issueId);
    }
}
