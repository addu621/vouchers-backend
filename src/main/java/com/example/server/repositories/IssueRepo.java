package com.example.server.repositories;

import com.example.server.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issue,String> {
    public List<Issue> findByIsClosedOrderByCreatedDate(Boolean flag);

    public Issue findByIssueId(Long issueId);

    /*@Query("select i from Issue i where orderItemId in (select orderItemId from VoucherOrderDetail vd inner join VoucherOrder vo on vd.orderId=vo.id and vo.buyerId=:userId)")
    public List<Issue> findIssuesByUserId(@Param("userId") long userId);*/

}
