package com.zyratechnologies.jwtrolebasedproject.service;

import com.zyratechnologies.jwtrolebasedproject.dtos.AuthResponseDto;
import com.zyratechnologies.jwtrolebasedproject.dtos.LoginRequest;
import com.zyratechnologies.jwtrolebasedproject.dtos.RegisterRequest;
import com.zyratechnologies.jwtrolebasedproject.enums.Role;
import com.zyratechnologies.jwtrolebasedproject.model.User;
import com.zyratechnologies.jwtrolebasedproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterRequest request) {

        // 1. Build the user object
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        // 2. Save user to DB
        userRepository.save(user);

        // 3. Generate token
        String token = jwtService.generateToken(user);

        // 4. Return token
        return AuthResponseDto.builder()
                .token(token)
                .build();
    }

    public AuthResponseDto login(LoginRequest request) {

        // 1. Verify username and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Load user from DB
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Generate token
        String token = jwtService.generateToken(user);

        // 4. Return token
        return AuthResponseDto.builder()
                .token(token)
                .build();
    }
}


