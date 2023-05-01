package com.pharma.reactives.config;

import com.pharma.reactives.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
        throws Exception {
        http
                .authorizeHttpRequests()
                    .requestMatchers("/accounts").hasRole("ADMIN")
                    .requestMatchers("/reactives", "/reactive/{id}").hasAnyRole("ADMIN", "USER", "EMPLOYEE")
                    .requestMatchers("/reactives/{id}/edit", "/reactives/new").hasAnyRole("ADMIN", "EMPLOYEE")

                    .requestMatchers("/medicines/{id}/edit", "/medicines/new").hasAnyRole("ADMIN", "EMPLOYEE")
                    .requestMatchers("/medicines", "/medicines/{id}").hasAnyRole("ADMIN", "USER", "EMPLOYEE")


                .requestMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                    .anyRequest().hasAnyRole("USER", "ADMIN", "EMPLOYEE")
                    .and()
                .formLogin()
                    .loginPage("/auth/login")
                    .defaultSuccessUrl("/reactives", true)
                    .failureUrl("/auth/login?error")
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/auth/login");

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
