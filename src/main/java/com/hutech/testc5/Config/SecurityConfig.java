package com.hutech.testc5.Config;

import com.hutech.testc5.Services.CustomDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomDetailService customDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                request->request.requestMatchers("/users").permitAll().
                        requestMatchers("/register").permitAll().
                        requestMatchers("/users/**").hasAuthority("MODIFIER")
                        .requestMatchers("/roles").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
        ).formLogin(httpSecurityFormLoginConfigurer->
                httpSecurityFormLoginConfigurer.loginPage("/login")
                        .successHandler(new AuthenticationSuccessHandler())
                        .permitAll()
                ).build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user").password(new BCryptPasswordEncoder().encode("password")).roles("USER").build();
//        UserDetails admin = User.builder()
//                .username("admin").password(new BCryptPasswordEncoder().encode("password")).roles("USER","MOFIDIER","ADMIN").build();
//        UserDetails modifier = User.builder()
//                .username("modifier").password(new BCryptPasswordEncoder().encode("password")).roles("USER","MOFIDIER").build();
//        return new InMemoryUserDetailsManager(user, admin, modifier);
        return customDetailService;
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
