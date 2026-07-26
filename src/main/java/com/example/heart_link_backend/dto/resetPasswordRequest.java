package com.example.heart_link_backend.dto;

import lombok.Data;

@Data
public class resetPasswordRequest {
    private String email;
    private String password;
}
