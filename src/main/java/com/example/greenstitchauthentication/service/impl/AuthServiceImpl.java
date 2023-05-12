package com.example.greenstitchauthentication.service.impl;

import com.example.greenstitchauthentication.model.Role;
import com.example.greenstitchauthentication.model.User;
import com.example.greenstitchauthentication.payload.request.LoginReq;
import com.example.greenstitchauthentication.payload.request.OtpVerificationReq;
import com.example.greenstitchauthentication.payload.request.RegisterReq;
import com.example.greenstitchauthentication.payload.request.ResetPasswordReq;
import com.example.greenstitchauthentication.payload.response.TokenRefreshRes;
import com.example.greenstitchauthentication.payload.response.UserLoginRes;
import com.example.greenstitchauthentication.repository.UserRepo;
import com.example.greenstitchauthentication.service.AuthService;
import com.example.greenstitchauthentication.service.CachingService;
import com.example.greenstitchauthentication.service.EmailService;
import com.example.greenstitchauthentication.service.jwt.JwtUtil;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final CachingService cachingService;
    private final EmailService emailService;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> signup(RegisterReq registerReq) throws MessagingException {
        if (userRepo.existsByEmailIgnoreCase(registerReq.getEmail().trim())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }
        registerReq.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        sendVerificationOtp(registerReq);
        return ResponseEntity.status(HttpStatus.OK).body("Please check your email for verification");
    }

    @Override
    public ResponseEntity<?> verifyAccount(OtpVerificationReq otpReq) throws ExecutionException {
        if (otpReq.getOtp() == cachingService.getOtp(otpReq.getEmail())) {
            RegisterReq registerReq = cachingService.getCachedUser(otpReq.getEmail());
            User user = new User(
                    registerReq.getFirstName(),
                    registerReq.getLastName(),
                    registerReq.getEmail().trim(),
                    registerReq.getPassword(),
                    Role.valueOf(registerReq.getRole())
            );
            userRepo.save(user);
            cachingService.clearUser(registerReq.getEmail());
            cachingService.clearOtp(registerReq.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Account verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }

    @Async
    @Override
    public void sendVerificationOtp(RegisterReq registerReq) throws MessagingException {
        int otp = cachingService.generateOtp(registerReq.getEmail());
        cachingService.cacheUserData(registerReq);

        emailService.sendMail(
                "innitt090@gmail.com",
                registerReq.getEmail(),
                "Email Verification",
                "Hello " + registerReq.getFirstName() + " " + registerReq.getLastName() + ",<br><br> You registered an account on GreenStitch, " +
                        "before being able to use your account you need to verify that this is your email address by verifying the OTP.<br> " +
                        "<h1>"+ otp + "</h1>" + "<br><br>Kind Regards, GreenStitch"
        );
    }

    @Async
    @Override
    public void sendForgetPasswordOtp(String email) throws MessagingException {
        int otp = cachingService.generateOtp(email);
        User user = userRepo.findByEmail(email);
        emailService.sendMail(
                "innitt090@gmail.com",
                email,
                "Reset Password",
                "Hello " + user.getFirstName() + " " + user.getLastName() + ",<br><br> Your OTP for resetting the password on GreenStitch is: <br>" +
                        "<h1>"+ otp + "</h1>" + "<br><br>Kind Regards, GreenStitch"
        );

    }

    @Override
    public ResponseEntity<?> login(LoginReq loginReq) {
        User userDetails = userDetailsService.loadUserByUsername(loginReq.getEmail().trim());
        try {
            if (passwordEncoder.matches(loginReq.getPassword(), userDetails.getPassword())) {
                String accessToken = jwtUtil.generateToken(userDetails);
                String refreshToken = jwtUtil.generateTokenFromEmail(userDetails.getEmail());

                return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, accessToken).body(
                    new UserLoginRes(
                        userDetails.getId(),
                        userDetails.getFirstName(),
                        userDetails.getLastName(),
                        userDetails.getEmail().trim(),
                        accessToken,
                        refreshToken
                    )
                );
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password incorrect");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist");
        }
    }

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken) {
        String email = jwtUtil.getEmailFromToken(refreshToken);
        User userDetails = userDetailsService.loadUserByUsername(email.trim());
        if (jwtUtil.validateToken(refreshToken, userDetails)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new TokenRefreshRes(jwtUtil.generateToken(userDetails), refreshToken)
            );
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is not valid");
    }

    @Override
    public ResponseEntity<?> forgotPassword(String email) throws MessagingException {
        if(userRepo.existsByEmailIgnoreCase(email.trim())) {
            sendForgetPasswordOtp(email.trim());
            return ResponseEntity.ok().body("Email sent with an OTP to reset your password.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not exist with the given email");
    }

    @Override
    public ResponseEntity<?> verifyResetOtp(OtpVerificationReq otpVerificationReq) throws ExecutionException {
        if (otpVerificationReq.getOtp() == cachingService.getOtp(otpVerificationReq.getEmail())) {

            return ResponseEntity.status(HttpStatus.OK).body("OTP is correct");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordReq resetPasswordReq) throws ExecutionException {
        if (resetPasswordReq.getOtp() == cachingService.getOtp(resetPasswordReq.getEmail())) {
            User user = userRepo.findByEmail(resetPasswordReq.getEmail().trim());
            user.setPassword(passwordEncoder.encode(resetPasswordReq.getPassword()));
            userRepo.save(user);
            cachingService.clearOtp(resetPasswordReq.getEmail().trim());
            return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }
}
