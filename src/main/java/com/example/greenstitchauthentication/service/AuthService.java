package com.example.greenstitchauthentication.service;

import com.example.greenstitchauthentication.payload.request.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public interface AuthService {
    ResponseEntity<?> signup(RegisterReq registerReq) throws MessagingException;
    ResponseEntity<?> verifyAccount(OtpVerificationReq otpVerificationReq) throws ExecutionException;
    ResponseEntity<?> login(LoginReq loginReq);
    ResponseEntity<?> refreshToken(String refreshToken);
    ResponseEntity<?> forgotPassword(String email) throws MessagingException;
    ResponseEntity<?> verifyResetOtp(OtpVerificationReq otpVerificationReq) throws ExecutionException;
    ResponseEntity<?> resetPassword(ResetPasswordReq resetPasswordReq) throws ExecutionException;
    void sendVerificationOtp(RegisterReq registerReq) throws MessagingException;
    void sendForgetPasswordOtp(String email) throws MessagingException;

}
