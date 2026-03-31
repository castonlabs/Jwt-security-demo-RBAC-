package com.zyratechnologies.jwtrolebasedproject.Controller;

import com.zyratechnologies.jwtrolebasedproject.dtos.AuthResponseDto;
import com.zyratechnologies.jwtrolebasedproject.dtos.LoginRequest;
import com.zyratechnologies.jwtrolebasedproject.dtos.RegisterRequest;
import com.zyratechnologies.jwtrolebasedproject.service.JwtAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

}
