package com.karmanno.languagelearningappapi.controller;

import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.dto.ApiResponse;
import com.karmanno.languagelearningappapi.dto.AuthRequest;
import com.karmanno.languagelearningappapi.dto.SignupRequest;
import com.karmanno.languagelearningappapi.service.AuthService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody SignupRequest authRequest) {
        return ResponseEntity.ok(
                Try.of(() -> ApiResponse.success(authService.signUp(
                        authRequest.getUsername(), authRequest.getPassword(), Language.valueOf(authRequest.getLanguage()))))
                    .onFailure(e -> ApiResponse.error(e.getMessage()))
                    .get()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(
                Try.of(() -> ApiResponse.success(authService.logIn(authRequest.getUsername(), authRequest.getPassword())))
                        .onFailure(e -> ApiResponse.error(e.getMessage()))
                        .get()
        );
    }
}
