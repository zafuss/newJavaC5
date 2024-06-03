package com.hutech.testc5.RequestEntities;

import com.hutech.testc5.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserUpdate {
    private String id;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String birthDay;
    private Role role;
}