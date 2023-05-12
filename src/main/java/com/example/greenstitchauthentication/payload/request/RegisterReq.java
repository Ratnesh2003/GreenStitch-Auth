package com.example.greenstitchauthentication.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class RegisterReq {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
}
