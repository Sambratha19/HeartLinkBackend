package com.example.heart_link_backend.dto;

import com.example.heart_link_backend.ENUM.InviteStatus;
import com.example.heart_link_backend.Entity.User;

public class InviteDto {
    private Long id;
    private InviteStatus status;

    private User sender;

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public InviteStatus getStatus() {
        return status;
    }

    public void setStatus(InviteStatus status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private User receiver;
}
