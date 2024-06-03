package com.hutech.testc5.Controllers;

import com.hutech.testc5.Entities.User;
import com.hutech.testc5.RequestEntities.LoginUser;
import com.hutech.testc5.RequestEntities.RegisterUser;
import com.hutech.testc5.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController {
    @Autowired
    private UserService userService;
    @GetMapping("/register")
    public String RegisterUser(Model model) {
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("registerUser", registerUser);
        return "Layout/Auth/Register";
    }

    @PostMapping("/register")
    public String CreateNewUser(RegisterUser  registerUser) {
        userService.RegisterUser(registerUser);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String LoginPage(Model model) {
        LoginUser loginUser = new LoginUser();
        model.addAttribute("loginUser", loginUser);
        return "Layout/Auth/Login";
    }
    @GetMapping("/me")
    public String me(Model model) {
        User user = userService.getCurrentUser();
        return "";
    }



}
