package com.example.heart_link_backend.Service;

import com.example.heart_link_backend.ENUM.InviteStatus;
import com.example.heart_link_backend.Entity.Invite;
import com.example.heart_link_backend.Entity.User;
import com.example.heart_link_backend.Repository.InviteRepository;
import com.example.heart_link_backend.Repository.UserRepository;
import com.example.heart_link_backend.Security.JwtService;
import com.example.heart_link_backend.dto.InviteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteService {

    private  final InviteRepository inviteRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final ConnectionService connectionService;

    public User getUserFromToken(String token){
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String email = jwtService.extractEmail(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String sendInvite(String token, String email){
        User sender=getUserFromToken(token);

        if(sender.getEmail().equals(email)){
            throw new RuntimeException("Don't Invite yourself");


        }

        Invite invite=new Invite();
        invite.setSenderId(sender.getId());
        invite.setReceiverEmail(email);
        invite.setStatus(InviteStatus.PENDING);

        Invite savesInvite= inviteRepository.save(invite);

        emailService.sendInviteEmail(
                email,
                sender.getEmail(),
                sender.getName(),
                savesInvite.getId()
        );
        return "Invite sent";

    }


    public String acceptInvite(Long inviteId){
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new RuntimeException("Invite not found"));

        // prevent multiple clicks
        if(invite.getStatus() != InviteStatus.PENDING){
            return "<h2>Invite already processed</h2>";
        }

        // ✅ UPDATE MYSQL
        invite.setStatus(InviteStatus.ACCEPTED);
        inviteRepository.save(invite);

        User sender = userRepository.findById(invite.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // receiver (IMPORTANT CHANGE)
        User receiver = userRepository.findByEmail(invite.getReceiverEmail())
                .orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(invite.getReceiverEmail());

            // 🔐 generate token for signup
            newUser.setVerified(false);

            return userRepository.save(newUser);
        });

        // create connection
        connectionService.createConnection(sender.getId(), receiver.getId());

        // update invite
        invite.setStatus(InviteStatus.ACCEPTED);
        inviteRepository.save(invite);

        return "<h2 style='color:green;'>Invite Accepted ❤️</h2>";
    }


    public String rejectInvite(Long inviteId){
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new RuntimeException("Invite not found"));

        if(invite.getStatus() != InviteStatus.PENDING){
            return "<h2>Invite already processed</h2>";
        }

        // ✅ UPDATE MYSQL
        invite.setStatus(InviteStatus.REJECTED);
        inviteRepository.save(invite);

        return "<h2 style='color:red;'>Invite Rejected ❌</h2>";
    }

    public List<InviteDto> getReceivedInvites(String token) {

        User currentUser = getUserFromToken(token);

        List<Invite> invites = inviteRepository.findByReceiverEmail(currentUser.getEmail());

        return invites.stream().map(invite -> {

            User sender = userRepository.findById(invite.getSenderId())
                    .orElse(null);

            InviteDto dto = new InviteDto();
            dto.setId(invite.getId());
            dto.setStatus(invite.getStatus());
            dto.setSender(sender);
            dto.setReceiver(currentUser);

            return dto;

        }).toList();
    }

    public List<InviteDto> getSentInvites(String token) {

        User currentUser = getUserFromToken(token);

        List<Invite> invites = inviteRepository.findBySenderId(currentUser.getId());

        return invites.stream().map(invite -> {

            User receiver = userRepository.findByEmail(invite.getReceiverEmail())
                    .orElse(null);

            InviteDto dto = new InviteDto();
            dto.setId(invite.getId());
            dto.setStatus(invite.getStatus());
            dto.setSender(currentUser);
            dto.setReceiver(receiver);

            return dto;

        }).toList();
    }
}
