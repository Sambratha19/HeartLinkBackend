package com.example.heart_link_backend.Repository;

import com.example.heart_link_backend.Entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConnectionRepository extends JpaRepository <Connection, Long> {
    boolean existsByUser1IdAndUser2Id(Long user1Id, Long user2Id);

    List<Connection> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);

    ;
}
