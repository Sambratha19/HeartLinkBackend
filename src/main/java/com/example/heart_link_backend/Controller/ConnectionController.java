package com.example.heart_link_backend.Controller;

import com.example.heart_link_backend.Entity.Connection;
import com.example.heart_link_backend.Entity.User;
import com.example.heart_link_backend.Repository.ConnectionRepository;
import com.example.heart_link_backend.Repository.UserRepository;
import com.example.heart_link_backend.Service.ConnectionService;
import com.example.heart_link_backend.Service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/connection")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ConnectionController {

    private final ConnectionRepository connectionRepository;
    private final InviteService inviteService;
    private final UserRepository userRepository;

    @GetMapping("/my")
    public List<User> getMyConnections(
            @RequestHeader("Authorization") String token
    ){
        User user=inviteService.getUserFromToken(token);

        List<Connection> connections=connectionRepository.findByUser1IdOrUser2Id(user.getId(), user.getId());

        List<Long> friendIds=new ArrayList<>();

        for(Connection c:connections){
            if(c.getUser1Id().equals(user.getId())){
                friendIds.add(c.getUser2Id());
            }else{
                friendIds.add(c.getUser1Id());
            }
        }


        return userRepository.findAllById(friendIds);
    }

}
