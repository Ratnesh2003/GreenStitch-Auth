package com.example.greenstitchauthentication.repository;

import com.example.greenstitchauthentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    Boolean existsByEmailIgnoreCase(String email);
    User findByEmail(String email);

}
