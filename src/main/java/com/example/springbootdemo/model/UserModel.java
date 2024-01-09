package com.example.springbootdemo.model;

import lombok.Value;

@Value
public class UserModel {
    Integer id;
    String userName;
    String accessToken;
    String password;
}
