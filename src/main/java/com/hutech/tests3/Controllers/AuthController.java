package com.hutech.tests3.Controllers;

import com.hutech.tests3.Entities.CustomUserDetail;
import com.hutech.tests3.Entities.User;
import com.hutech.tests3.RequestEntities.RegisterUser;
import com.hutech.tests3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private JavaMailSender emailSender;

    @GetMapping("/register")
    public String Register(Model model) {
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("registerUser", registerUser);
        return "Layout/Auth/Register";
    }

    @PostMapping("/register")
    public String CreateUser(RegisterUser registerUser) {
        userService.RegisterUser(registerUser);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String Login() {
        return "Layout/Auth/Login";
    }

    @GetMapping("/me")
    public String GetCurrentUser(@AuthenticationPrincipal CustomUserDetail customUserDetail, Model model) {
        User user = customUserDetail.getUser();
        model.addAttribute("user", user);
        return "Layout/Auth/me";
    }

    @GetMapping("/changepassword")
    public String ChangePassword(@AuthenticationPrincipal CustomUserDetail customUserDetail, Model model) {
        User user = customUserDetail.getUser();
        model.addAttribute("user", user);
        return "Layout/Auth/changepassword";
    }

    @PostMapping("/change_password")
    public String ChangePassword_Submit(@AuthenticationPrincipal CustomUserDetail customUserDetail, @RequestParam("password") String password, Model model) {
        try {
            User user = customUserDetail.getUser();
            userService.ChangePassword(user, password);
            return "redirect:/changepassword?done";
        } catch (Exception e) {
            return "redirect:/changepassword?error";
        }
    }

    @GetMapping("/forgotpassword")
    public String ForgotPassword() {
        return "Layout/Auth/forgotpassword";
    }

    @PostMapping("/forgot_password")
    public String ForgotPassword_Submit(@RequestParam("email") String email) {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            user = userService.createTokenResetPassword(user);
            String token = user.getResetPasswordToken();
            String URL = "http://localhost:8080/reset_password?token=" + token;
            SendMail(user.getEmail(), URL);
            return "redirect:/forgotpassword?done";
        }
        return "redirect:/forgotpassword?error";
    }

    @GetMapping("/reset_password")
    public String ResetPassword(@Param("token") String token, Model model) {
        User user = userService.findUserByResetPasswordToken(token);
        if (user == null) {
            return "redirect:/forgotpassword";
        } else {
            if (user.getResetPasswordTokenExpired().getTime() > System.currentTimeMillis()) {
                model.addAttribute("token", token);
                return "Layout/Auth/resetpassword";
            } else {
                return "redirect:/forgotpassword";
            }

        }
    }

    @PostMapping("/reset_password")
    public String ResetPassword_Submit(@RequestParam("token") String token, @RequestParam("password") String password) {
        User user = userService.findUserByResetPasswordToken(token);
        if (user == null) {
            return "redirect:/forgotpassword";
        } else {
            userService.updateResetPasswordToken(user, password);
            return "redirect:/login";
        }
    }
    public void SendMail(String DesMail, String URL){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("heegeheheheheh@gmail.com");
        message.setTo(DesMail);
        message.setSubject("click vo di anh chai");
        message.setText("<p>Hello,</p>" +
                "<p>You have requested to reset your password.</p>" +
                "<p>Click the link below to change your password:</p>" +
                "<p><a href=" + URL + ">Change my password</a></p>" +
                "<br><p>Ignore this email if you do remember your password," +
                "or you have not made the request.</p>");
        emailSender.send(message);
    }



}
