package com.hutech.tests3.Services;

import com.hutech.tests3.Entities.User;
import com.hutech.tests3.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> listAuth = new ArrayList<>();
        for (String role: BuildRolesFromRole(user.getRole().getRole_name())){
            listAuth.add(new SimpleGrantedAuthority(role));
        }
        UserDetails userDetails =  org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword()).authorities(listAuth).build();
        return userDetails;
    }
    public List<String> BuildRolesFromRole(String role){
        String roles = "ADMIN,MODIFIER,USER";
        int index = role.indexOf(role);
        return Arrays.stream(roles.substring(index).split(",")).toList();
    }

}
