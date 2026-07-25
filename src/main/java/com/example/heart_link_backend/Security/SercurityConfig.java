package com.example.heart_link_backend.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SercurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {}) // 🔥 ADD THIS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/avatars/**").permitAll()
                        .requestMatchers("/connection/**").permitAll()
                        .requestMatchers("/invite/**").permitAll()
                        .requestMatchers("/message/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .anyRequest().authenticated()
                                .anyRequest().permitAll()
                );


        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
