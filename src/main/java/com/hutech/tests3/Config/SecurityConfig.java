package com.hutech.tests3.Config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.AbstractConfiguredSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                request->request
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/users").hasRole("USER")
                        .requestMatchers("/users/**").hasRole("MODIFIER")
                        .requestMatchers("/roles").hasRole("ADMIN")
                        .anyRequest().permitAll()
        ).formLogin(AbstractConfiguredSecurityBuilder
                ->AbstractConfiguredSecurityBuilder.permitAll()
        ).build();
    }
    @Bean
    public UserDetailsService UserDetailsService() {
        UserDetails user = User.builder()
                .username("user").password("password").roles("USER").build();
        UserDetails modifier = User.builder()
                .username("modifier").password("password").roles("MODIFIER").build();
        UserDetails admin = User.builder()
                .username("admin").password(passwordEncoder().encode("password")).roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, modifier, admin);
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(UserDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
