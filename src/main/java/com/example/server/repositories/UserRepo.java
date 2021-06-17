package com.example.server.repositories;

import com.example.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,String>{
    public User findByUserEmail(String email);
}
