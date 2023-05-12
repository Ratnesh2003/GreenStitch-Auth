package com.example.greenstitchauthentication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public ResponseEntity<?> homeRoute() {
        return ResponseEntity.status(HttpStatus.OK).body("This is your home route");
    }
}
