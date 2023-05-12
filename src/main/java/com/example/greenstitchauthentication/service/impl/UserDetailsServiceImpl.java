package com.example.greenstitchauthentication.service.impl;

import com.example.greenstitchauthentication.model.User;
import com.example.greenstitchauthentication.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            return User.build(user);
        } else {
            throw new UsernameNotFoundException("User does not exist.");
        }

    }
}
