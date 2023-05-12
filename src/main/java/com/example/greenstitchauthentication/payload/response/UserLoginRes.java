package com.example.greenstitchauthentication.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginRes {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String accessToken;
    private String refreshToken;
}
