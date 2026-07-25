package com.example.heart_link_backend.Entity;

import com.example.heart_link_backend.ENUM.InviteStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="invites")
@NoArgsConstructor
@AllArgsConstructor
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
    @Column(name="sender_id")
    private Long senderId;

    @Column(name="receiver_email")
    private String receiverEmail;


    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAT;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private InviteStatus status;

    public InviteStatus getStatus() {
        return status;
    }

    public void setStatus(InviteStatus status) {
        this.status = status;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }



    @PrePersist
    protected void onCreate(){
        createdAT=LocalDateTime.now();
    }
}
