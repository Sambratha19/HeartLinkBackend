package com.example.heart_link_backend.Service;

import com.example.heart_link_backend.ENUM.MessageStatus;
import com.example.heart_link_backend.Entity.Messages;
import com.example.heart_link_backend.Entity.User;
import com.example.heart_link_backend.Repository.MessageRespository;
import com.example.heart_link_backend.Repository.UserRepository;
import com.example.heart_link_backend.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.security.autoconfigure.actuate.web.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MessageService {
    private final MessageRespository messageRespository;
    private final JwtService jwtService;
    private final UserRepository userRepository;



    public User getUserFromToken(String token){
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }


        String email = jwtService.extractEmail(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Messages sendMessage(String token, Messages message){
        String jwt = token.substring(7);
        String email = jwtService.extractEmail(jwt);

        User sender=userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        message.setSenderId(sender.getId());
        message.setStatus(MessageStatus.sent);

        return messageRespository.save(message);
    }

    public List<Messages> getChat(Long senderId, Long receiverId) {
        return messageRespository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(senderId, receiverId, receiverId, senderId);
    }

    public Messages markAsSeen(Long messageId) {
        Messages msg = messageRespository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        msg.setStatus(MessageStatus.sent);
        return messageRespository.save(msg);
    }
}
