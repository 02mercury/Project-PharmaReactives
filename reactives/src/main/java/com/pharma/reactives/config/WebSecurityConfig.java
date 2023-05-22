package com.pharma.reactives.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clasa de configurare pentru securitatea aplicatiei web.
 * @author : Bodiu Dumitru
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    /**
     * Configurarea filtrului de securitate implicit.
     * Defineste permisiunile si rolurile utilizatorilor.
     * @param http HttpSecurity configurat
     * @return SecurityFilterChain
     * @throws Exception in cazul unei erori la configurare
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
        throws Exception {
        http
                .authorizeHttpRequests()
                    .requestMatchers("/accounts").hasRole("ADMIN")
                    .requestMatchers("/dashboard").hasRole("ADMIN")
                    .requestMatchers("/reactives", "/reactive/{id}").hasAnyRole("ADMIN", "USER", "EMPLOYEE")
                    .requestMatchers("/reactives/{id}/edit", "/reactives/new", "/reactives/export").hasAnyRole("ADMIN", "EMPLOYEE")

                    .requestMatchers("/medicines/{id}/edit", "/medicines/new").hasAnyRole("ADMIN", "EMPLOYEE")
                    .requestMatchers("/medicines", "/medicines/{id}").hasAnyRole("ADMIN", "USER", "EMPLOYEE")

                    .requestMatchers("/cart").authenticated()

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
                    .logoutSuccessUrl("/auth/login")
                    .and()
                .rememberMe()
                    .key("QweRtyuiopAsdfgHjKlzxcmnbV0123456789")
                .tokenValiditySeconds(3 * 24 * 60 * 60); // sesiunea va expira in 3 zile

        return http.build();
    }

    /**
     * Creeaza un encoder de parole folosind algoritmul bcrypt.
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
