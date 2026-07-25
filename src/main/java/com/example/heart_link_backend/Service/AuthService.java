package com.example.heart_link_backend.Service;

import com.example.heart_link_backend.Entity.User;
import com.example.heart_link_backend.Repository.UserRepository;
import com.example.heart_link_backend.Security.JwtService;
import com.example.heart_link_backend.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpMessageConverterAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final EmailService emailService;

    // ✅ SIGNUP WITH EMAIL VERIFICATION
    public String signup(AuthRequest request) {
        User user = repo.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            user = new User();
            user.setEmail(request.getEmail());

        } else if (user.getPassword() != null && user.getName() != null) {
            return "User already exists";
        }
        user.setName(request.getName());
        user.setPassword(encoder.encode(request.getPassword()));

        user.setVerified(false);
        user.setVerificationToken(UUID.randomUUID().toString());
        emailService.sendVerificationEmail(user.getEmail(), user.getVerificationToken());

        repo.save(user);

        return "User registered successfully. Please verify your email.";


    }

    // ✅ EMAIL VERIFICATION METHOD (IMPORTANT)
    public String verifyEmail(String token) {

        User user = repo.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        user.setVerified(true);
        user.setVerificationToken(null);

        repo.save(user);

        return "Email verified successfully. You can now login.";
    }

    // ✅ LOGIN (ONLY VERIFIED USERS ALLOWED)
    public String login(AuthRequest request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getVerified()) {
            throw new RuntimeException("Please verify your email first");
        }

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        user.setActive(true);
        user.setLastSeen(LocalDateTime.now());
        repo.save(user);
        return jwtService.generateToken(user.getEmail());
    }

    public void logout(String token){
        String jwtToken = token.startsWith("Bearer ")
                ? token.substring(7)
                : token;

        String email=jwtService.extractEmail(jwtToken);

        User user=repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setActive(false);
        user.setLastSeen(LocalDateTime.now());

        repo.save(user);
    }

    public void updateToggleLastSeen(String token, boolean showLstSeen){
        String jwtToken = token.startsWith("Bearer ")
                ? token.substring(7)
                : token;

        String email=jwtService.extractEmail(jwtToken);

        User user=repo.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        user.setShowLastSeen(showLstSeen);
        repo.save(user);
    }


    public User getUserFromToken(String token) {

        String email = jwtService.extractEmail(token.substring(7));

        return repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public String updateAvatar(String token, String avatarUrl){
        if(token.startsWith("Bearer ")){
            token=token.substring(7);

        }

        String email= jwtService.extractEmail(token);

        User user=repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        user.setAvatar(avatarUrl);
        repo.save(user);

        return "Avatar Updates";

    }


    public String updateName(String token, String newName){
        if(token.startsWith("Bearer ")){
            token=token.substring(7);

        }

        String email= jwtService.extractEmail(token);

        User user=repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        user.setName(newName);
        repo.save(user);

        return "Name Updated";
    }
    public String updateBio(String token, String newBio){
        if(token.startsWith("Bearer ")){
            token=token.substring(7);

        }

        String email= jwtService.extractEmail(token);

        User user=repo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        user.setBio(newBio);
        repo.save(user);

        return "Bio Updated";
    }


}