package com.example.heart_link_backend.Repository;

import com.example.heart_link_backend.ENUM.InviteStatus;
import com.example.heart_link_backend.Entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {

    List<Invite> findByReceiverEmail(String email);
    List<Invite> findByStatus(InviteStatus status);

    List<Invite> findBySenderId(Long senderId);
    boolean existsBySenderIdAndReceiverEmailAndStatus(Long senderId, String email, String status);

}
