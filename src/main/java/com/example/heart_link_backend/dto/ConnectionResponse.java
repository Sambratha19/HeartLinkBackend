package com.example.heart_link_backend.dto;


import com.example.heart_link_backend.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class ConnectionResponse {
    private  int totalConnections;
    private List<User> connections;
    private List<User> receivedRequests;
    private List<User> sendRequests;
}
