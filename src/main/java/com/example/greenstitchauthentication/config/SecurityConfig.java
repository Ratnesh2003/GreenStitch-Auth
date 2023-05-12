package com.example.greenstitchauthentication.config;


import com.example.greenstitchauthentication.service.jwt.AuthTokenFilter;
import com.example.greenstitchauthentication.service.jwt.JwtAuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final AuthTokenFilter authTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions()
                .disable()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs.yaml")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**")).permitAll()
//                .requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/error/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
//        http.httpBasic().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
