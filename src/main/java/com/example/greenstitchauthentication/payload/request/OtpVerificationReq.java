package com.example.greenstitchauthentication.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class OtpVerificationReq {
    private String email;
    private int otp;
}
