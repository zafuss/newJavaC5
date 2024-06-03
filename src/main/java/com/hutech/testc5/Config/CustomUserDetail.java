package com.hutech.testc5.Config;

import com.hutech.testc5.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {
    private User user;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(String string: BuildRoleFromRole(user.getRole().getRole_name())) {
            authorities.add(new SimpleGrantedAuthority(string));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLastName();
    }

    public String getDisplayName(){
        return "HEHE";
    }

    private List<String> BuildRoleFromRole(String role) {
        String roles = "ADMIN,MODIFIER,USER";
        int position = roles.indexOf(role);
        return Arrays.stream(roles.substring(position).split(",")).toList();
    }
}
