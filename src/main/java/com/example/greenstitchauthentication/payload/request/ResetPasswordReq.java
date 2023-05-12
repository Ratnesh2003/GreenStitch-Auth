package com.example.greenstitchauthentication.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetPasswordReq {
    private String email;
    private String password;
    private int otp;
}
