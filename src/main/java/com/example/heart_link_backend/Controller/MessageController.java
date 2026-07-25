package com.example.heart_link_backend.Controller;

import com.example.heart_link_backend.Entity.Messages;
import com.example.heart_link_backend.Service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://heart-link-lilac.vercel.app")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public Messages sendMessage(
            @RequestBody Messages messages,
            @RequestHeader("Authorization") String token
    ){
        return messageService.sendMessage(token, messages);
    }

    @GetMapping("/{senderId}/{receiverId}")
    public List<Messages> getChat (
            @PathVariable Long senderId,
            @PathVariable Long receiverId
    ){
return messageService.getChat(senderId, receiverId);
    }

    @PutMapping("/seen/{id}")
    public Messages markSeen(@PathVariable Long id) {
        return messageService.markAsSeen(id);
    }
}
