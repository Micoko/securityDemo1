package com.example.demo1.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(request -> request
            .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
            .requestMatchers("/status", "/images/**", "/view/join", "/auth/join").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(login -> login
            .loginPage("/view/login")
            .loginProcessingUrl("/login-process")
            .usernameParameter("userid")
            .passwordParameter("pw")
            .defaultSuccessUrl("/view/dashboard", true)
            .permitAll()
        )
        .logout(withDefaults())
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .maximumSessions(1)
            .maxSessionsPreventsLogin(false)
        );

    return http.build();
  }
}
