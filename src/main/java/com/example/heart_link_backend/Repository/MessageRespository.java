package com.example.heart_link_backend.Repository;

import com.example.heart_link_backend.Entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRespository extends JpaRepository<Messages, Long> {
    List<Messages> findBySenderIdAndReceiverId(Long sendId, Long receiverId);
    List<Messages> findByReceiverIdAndStatus(Long receiverId, String status);
    List<Messages> findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(
            Long senderId1, Long receiverId1,
            Long senderId2, Long receiverId2);
}
