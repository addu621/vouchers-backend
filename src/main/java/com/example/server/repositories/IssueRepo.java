package com.example.server.repositories;

import com.example.server.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issue,String> {
    public List<Issue> findByIsClosedOrderByCreatedDate(Boolean flag);

    public Issue findByIssueId(Long issueId);
}
