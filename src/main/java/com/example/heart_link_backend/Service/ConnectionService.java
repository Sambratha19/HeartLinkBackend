package com.example.heart_link_backend.Service;

import com.example.heart_link_backend.ENUM.InviteStatus;
import com.example.heart_link_backend.Entity.Connection;
import com.example.heart_link_backend.Entity.Invite;
import com.example.heart_link_backend.Entity.User;
import com.example.heart_link_backend.Repository.ConnectionRepository;
import com.example.heart_link_backend.Repository.InviteRepository;
import com.example.heart_link_backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionRepository connectionRepository;

    public void createConnection(Long userA, Long userB){
        Long user1 = Math.min(userA, userB);
        Long user2 = Math.max(userA, userB);

        System.out.println("USERS---"+user1+"----"+user2);


        boolean exists = connectionRepository.existsByUser1IdAndUser2Id(user1, user2);

        if(exists){
            throw new RuntimeException("Already Connected");

        }

        Connection connection = new Connection();

        connection.setUser1Id(user1);
        connection.setUser2Id(user2);
        System.out.println("USERS---"+connection.getUser1Id()+"----"+connection.getUser2Id());
        connectionRepository.save(connection);
    }

}
