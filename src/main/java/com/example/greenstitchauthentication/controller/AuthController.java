package com.example.greenstitchauthentication.controller;

import com.example.greenstitchauthentication.payload.request.*;
import com.example.greenstitchauthentication.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody RegisterReq registerReq) throws MessagingException {
        return this.authService.signup(registerReq);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyAccount(@Valid @RequestBody OtpVerificationReq otpRequest) throws ExecutionException {
        return this.authService.verifyAccount(otpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq loginReq) {
        return this.authService.login(loginReq);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenReq tokenReq) {
        return this.authService.refreshToken(tokenReq.getToken());
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) throws MessagingException {
        return this.authService.forgotPassword(email);
    }

    @PostMapping("/verify-reset-otp")
    public ResponseEntity<?> verifyResetOtp(@Valid @RequestBody OtpVerificationReq otpReq) throws ExecutionException {
        return this.authService.verifyResetOtp(otpReq);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordReq resetPasswordReq) throws ExecutionException {
        return this.authService.resetPassword(resetPasswordReq);
    }
}
