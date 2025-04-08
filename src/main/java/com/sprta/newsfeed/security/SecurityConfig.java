package com.sprta.newsfeed.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable()) // REST API용 csrf 보호 비활성화
                .authorizeHttpRequests((authorize -> authorize
                        .requestMatchers("/**")
                        .permitAll())) // 모든 경로에서 인증 없이 모든 사용자가 접근 가능
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER)); // 새로운 세션 자동 생성 X

        return http.build(); // 설정 반환

    }

}
