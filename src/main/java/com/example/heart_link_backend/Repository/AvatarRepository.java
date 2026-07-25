package com.example.heart_link_backend.Repository;

import com.example.heart_link_backend.Entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    @Query("SELECT a FROM Avatar a WHERE LOWER(a.tags) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Avatar> searchAvatars(String search);
}
