package com.example.server.services;

import com.example.server.dto.request.BlockedUsersRequest;
import com.example.server.dto.response.GenericResponse;
import com.example.server.entities.BlockedUsers;
import com.example.server.entities.Person;
import com.example.server.repositories.BlockedUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private BlockedUsersRepository blockedUsersRepository;

    public GenericResponse blockUser(BlockedUsersRequest person) {
        GenericResponse genericResponse = new GenericResponse();
        if(blockedUsersRepository.getById(person.getId().toString())!=null)
        {
            genericResponse.setStatus(404);
            genericResponse.setMessage("User already blocked!!!");
            return genericResponse;
        }
        BlockedUsers newBlockedUser = new BlockedUsers();
        newBlockedUser.setId(person.getId());
        newBlockedUser.setEmail(person.getEmail());
        newBlockedUser.setDescription(person.getDescription());
        blockedUsersRepository.save(newBlockedUser);
        genericResponse.setStatus(200);
        genericResponse.setMessage("User blocked!!!");
        return genericResponse;
    }
}
