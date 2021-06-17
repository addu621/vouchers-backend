package com.example.server.repositories;

import com.example.server.entities.User;
import com.example.server.entities.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDao,String>{
     UserDao findByUsername(String email);
}
