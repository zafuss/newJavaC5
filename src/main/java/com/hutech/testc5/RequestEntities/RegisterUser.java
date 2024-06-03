package com.hutech.testc5.RequestEntities;


import lombok.Data;

@Data
public class RegisterUser {
    private String username;
    private String password;
    private String email;
}
