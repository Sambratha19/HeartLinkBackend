package com.example.heart_link_backend.Controller;

import com.example.heart_link_backend.Entity.User;
import com.example.heart_link_backend.Repository.UserRepository;
import com.example.heart_link_backend.Service.AuthService;
import com.example.heart_link_backend.dto.AuthRequest;
import com.example.heart_link_backend.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService service;

    @PostMapping("/signup")
    public String signup(@RequestBody AuthRequest request) {
        return service.signup(request);
    }



    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody AuthRequest request
    ) {
        String token = service.login(request);
        return new AuthResponse(token);
    }

    @PostMapping("/logout")
    public String  logout(
            @RequestHeader("Authorization") String token
    ) {
    service.logout(token);
    return "Logged out";
    }

    @PutMapping("/toggle-last-seen")
    public String toggleLastSeen(
            @RequestHeader("Authorization") String token,
            @RequestParam boolean showLastSeen) {

        service.updateToggleLastSeen(token, showLastSeen);
        return "Updated";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {
        return service.verifyEmail(token);
    }

    @GetMapping("/me")
    public User getMe(@RequestHeader("Authorization") String token) {
        return service.getUserFromToken(token);
    }

    @PutMapping("/avatar")
    public String updateAvatar(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body
    ) {
        String avatarUrl = body.get("avatar");
        return service.updateAvatar(token, avatarUrl);
    }

    @PutMapping("/name")
    public String updateName(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body
    ) {
        String newName = body.get("name");
        return service.updateName(token, newName);
    }

    @PutMapping("/bio")
    public String updateBio(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> body
    ) {
        String newBio = body.get("bio");
        return service.updateBio(token, newBio);
    }


}
