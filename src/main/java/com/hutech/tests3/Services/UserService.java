package com.hutech.tests3.Services;

import com.hutech.tests3.Config.Utility;
import com.hutech.tests3.Entities.Role;
import com.hutech.tests3.Entities.User;
import com.hutech.tests3.Repositories.RoleRepository;
import com.hutech.tests3.Repositories.UserRepository;
import com.hutech.tests3.RequestEntities.RegisterUser;
import com.hutech.tests3.RequestEntities.RequestUser;
import com.hutech.tests3.RequestEntities.RequestUserUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    public List<User> findAll() {
        return userRepository.findAll();
    }
    public User findById(String id) {
        return userRepository.findById(id).get();
    }
    public User CreateUser(RequestUser requestUser) {
        try {
            User user = new User();
            user.setUsername(requestUser.getUsername());
            user.setPassword(requestUser.getPassword());
            user.setEmail(requestUser.getEmail());
            user.setFirstName(requestUser.getFirstName());
            user.setLastName(requestUser.getLastName());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(requestUser.getBirthDate());
            user.setBirthDay(date);
            user.setEnabled(true);
            Role role = roleRepository.findOneByName("USER");
            user.setRole(role);
            return userRepository.save(user);
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }
    public User UpdateUser(RequestUserUpdate requestUserUpdate) {
        try {
            User user = userRepository.findById(requestUserUpdate.getId()).get();
            user.setFirstName(requestUserUpdate.getFirstName());
            user.setLastName(requestUserUpdate.getLastName());
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(requestUserUpdate.getBirthDay());
            user.setBirthDay(date);
            user.setPassword(requestUserUpdate.getPassword());
            user.setEmail(requestUserUpdate.getEmail());
            user.setRole(requestUserUpdate.getRole());
            return userRepository.save(user);
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
    }
    public User RegisterUser(RegisterUser registerUser) {
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(registerUser.getPassword()));
        user.setEmail(registerUser.getEmail());
        user.setRole(roleRepository.findOneByName("USER"));
        return userRepository.save(user);
    }
    public User ChangePassword(User user, String password){
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        return userRepository.save(user);
    }
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User createTokenResetPassword(User user) {
        String token = Utility.generateRandomString(45);
        user.setResetPasswordToken(token);
        user.setResetPasswordTokenExpired(new Date(System.currentTimeMillis()+10*60*1000));
        return userRepository.save(user);
    }
    public User findUserByResetPasswordToken(String token) {
        return userRepository.findByToken(token);
    }
    public User updateResetPasswordToken(User user, String password) {
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setResetPasswordToken("");
        user.setResetPasswordTokenExpired(null);
        return userRepository.save(user);
    }
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public void UpdateCountFail(User user) {
        if (user.isEnabled()) {
            int count = userRepository.countFail(user.getUsername());
            count += 1;
            user.setCountFail(count);
            if (count == 4) {
                user.setEnabled(false);
                user.setCountFail(0);
                user.setLockExpired(new Date(System.currentTimeMillis() + 10 * 2 * 1000));
            }
        } else {
            if (user.getLockExpired() != null) {
                if (user.getLockExpired().getTime() < System.currentTimeMillis()) {
                    user.setLockExpired(null);
                    user.setEnabled(true);
                }
            }
        }
        userRepository.save(user);
    }
    public void resetLockAccount(User user){
        user.setCountFail(0);
        user.setLockExpired(null);
        userRepository.save(user);
    }

}