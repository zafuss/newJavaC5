package com.hutech.tests3.Config;

import com.hutech.tests3.Entities.CustomUserDetail;
import com.hutech.tests3.Entities.User;
import com.hutech.tests3.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class SuccessHandle extends SimpleUrlAuthenticationSuccessHandler {
    private final UserService userService;

    public SuccessHandle(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = ((CustomUserDetail)authentication.getPrincipal()).getUser();
        if(user.isEnabled()){
            userService.resetLockAccount(user);
        }else{
            if(user.getLockExpired().getTime()<System.currentTimeMillis()){
                throw new ServletException("User is locked");
            }else{
                userService.resetLockAccount(user);
            }
        }
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        if(isAdmin){
            setDefaultTargetUrl("/roles");
        }else{
            setDefaultTargetUrl("/users");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
