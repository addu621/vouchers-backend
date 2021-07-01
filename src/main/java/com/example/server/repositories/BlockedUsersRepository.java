package com.example.server.repositories;

import com.example.server.entities.BlockedUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedUsersRepository extends JpaRepository<BlockedUsers,Long> {
    public BlockedUsers findByEmail(String email);
}
