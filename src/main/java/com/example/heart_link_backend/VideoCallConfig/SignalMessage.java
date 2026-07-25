package com.example.heart_link_backend.VideoCallConfig;

import lombok.Data;

@Data
public class SignalMessage {
    private String type;
    private String sender;
    private String receiver;
    private Object data;
}
