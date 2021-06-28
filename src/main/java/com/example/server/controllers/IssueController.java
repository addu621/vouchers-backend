package com.example.server.controllers;

import com.example.server.dto.response.GenericResponse;
import com.example.server.services.IssueService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class IssueController {

    @Autowired
    public IssueService issueService;

    @PostMapping("/issue/submit")
    public GenericResponse submitIssue(@RequestBody Map<String,String> issue){
        GenericResponse genericResponse = new GenericResponse();
        return issueService.submitIssue(Long.parseLong(issue.get("transactionId")), Long.parseLong(issue.get("voucherId")), issue.get("comment"));
    }
}
