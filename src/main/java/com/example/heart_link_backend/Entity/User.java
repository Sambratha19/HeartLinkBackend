package com.example.heart_link_backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column (name="email", unique = true)
    private String email;

    @Column(name="is_verified")
    private Boolean isVerified;

    @Column(name="avatar")
    private String avatar;

    @Column(name="bio")
    private String bio;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "last_seen")
    private LocalDateTime lastSeen;
    @Column(name = "show_last_seen")
    private boolean showLastSeen;

    public boolean isShowLastSeen() {
        return showLastSeen;
    }

    public void setShowLastSeen(boolean showLastSeen) {
        this.showLastSeen = showLastSeen;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verification_token) {
        this.verificationToken = verification_token;
    }

    @Column(name="verification_token")
    private String verificationToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="password")
    private  String password;
}
