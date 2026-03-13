package com.example.case_study_m4_team1.config;

import com.example.case_study_m4_team1.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/login",
                                "/register",
                                "/welcome",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .requestMatchers("/teacher/**").hasRole("TEACHER")

                        .requestMatchers("/user/**").hasRole("USER")

                        .requestMatchers("/booking/**").hasRole("USER")

                        .anyRequest().authenticated()
                )

                .formLogin(login -> login

                        .loginPage("/login")

                        .loginProcessingUrl("/login")

                        .defaultSuccessUrl("/welcome", true)

                        .failureUrl("/login?error")

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl("/login?logout")

                        .invalidateHttpSession(true)

                        .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(e ->
                        e.accessDeniedPage("/403")
                )
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
    }

}