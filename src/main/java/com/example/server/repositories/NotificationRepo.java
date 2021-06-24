package com.example.server.repositories;

import com.example.server.entities.Notification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends CrudRepository<Notification,Long> {

    public List<Notification> findAllByReceiverIdOrderByCreatedDateDesc(Long receiverId);

}
