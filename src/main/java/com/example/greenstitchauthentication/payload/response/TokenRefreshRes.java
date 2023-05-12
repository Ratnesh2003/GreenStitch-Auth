package com.example.greenstitchauthentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRefreshRes {
    private String refreshToken;
    private String accessToken;
}
