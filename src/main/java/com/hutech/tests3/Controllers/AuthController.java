package com.hutech.tests3.Controllers;

import com.hutech.tests3.Entities.CustomUserDetail;
import com.hutech.tests3.Entities.User;
import com.hutech.tests3.RequestEntities.RegisterUser;
import com.hutech.tests3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String Register(Model model){
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("registerUser", registerUser);
        return "Layout/Auth/Register";
    }
    @PostMapping("/register")
    public String CreateUser(RegisterUser registerUser){
        userService.RegisterUser(registerUser);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String Login(){
        return "Layout/Auth/Login";
    }

    @GetMapping("/me")
    public String GetCurrentUser(@AuthenticationPrincipal CustomUserDetail customUserDetail, Model model){
        User user = customUserDetail.getUser();
        model.addAttribute("user", user);
        return "Layout/Auth/me";
    }
    @GetMapping("/changepassword")
    public String ChangePassword(@AuthenticationPrincipal CustomUserDetail customUserDetail, Model model){
        User user = customUserDetail.getUser();
        model.addAttribute("user", user);
        return "Layout/Auth/changepassword";
    }
    @PostMapping("/change_password")
    public String ChangePassword_Submit(@AuthenticationPrincipal CustomUserDetail customUserDetail, @RequestParam("password") String password, Model model){
        try {
            User user = customUserDetail.getUser();
            userService.ChangePassword(user,password);
            return "redirect:/changepassword?done";
        }catch (Exception e){
            return "redirect:/changepassword?error";
        }
    }
}
