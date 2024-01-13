package com.ride.amt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

import static org.springframework.security.config.Customizer.*;

@Configuration
public class SpringSecurityConfiguration {
    //LDAP or DB
    //In Memory

//    InMemoryUserDetailsManager
//    InMemoryUserDetailsManager(UserDetails... users)

    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {

        UserDetails userDetails1 = createNewUser("ride", "pw");
        UserDetails userDetails2 = createNewUser("ride-dev", "pw");

        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    private UserDetails createNewUser(String username, String password) {
        Function<String, String> passwordEncoder
                = input -> passwordEncoder().encode(input);

        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();
        return userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 모든 요청을 승인
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated());
        // 승인되지 않은 요청이 있으면 formLogin 설정, 사용자 이름과 패스워드 수집
        http.formLogin(withDefaults());
        // csrf 비활성화, 애플리케이션에서 프레임 사용 비활성화
        http.csrf(
                csrf -> csrf.disable());
        http.headers(
                headers -> headers.frameOptions(
                        frameOptions -> frameOptions.disable()));

        return http.build();
    }
}
