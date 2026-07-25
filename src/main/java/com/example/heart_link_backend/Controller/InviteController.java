package com.example.heart_link_backend.Controller;

import com.example.heart_link_backend.Entity.Invite;
import com.example.heart_link_backend.Service.InviteService;
import com.example.heart_link_backend.dto.InviteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invite")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class InviteController {

    @Autowired
    private final InviteService inviteService;

    @PostMapping("/send")
    public String sendInvite(
            @RequestHeader("Authorization") String token,
            @RequestParam String email
    ){
        return inviteService.sendInvite(token, email);
    }

    @GetMapping("/accept/{inviteId}")
    public String acceptInvite(@PathVariable Long inviteId) {
        return inviteService.acceptInvite(inviteId);
    }

    @GetMapping("/reject/{inviteId}")
    public String rejectInvite(@PathVariable Long inviteId) {
        return inviteService.rejectInvite(inviteId);
    }

    @GetMapping("/received")
    public  List<InviteDto> getReceivedRequest(
            @RequestHeader("Authorization") String token
    ){
        return inviteService.getReceivedInvites(token);
    }

    @GetMapping("/sent")
    public List<InviteDto> getSentResponse(
            @RequestHeader("Authorization") String token

    ){
        return inviteService.getSentInvites(token);

    }

}
