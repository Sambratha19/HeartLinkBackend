package com.example.heart_link_backend.VideoCallConfig;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class VideoCallController {

    @MessageMapping("/signal")
    @SendTo("/topic/messages")
    public SignalMessage signal(
            SignalMessage message
    ){
        return message;
    }
}
