package com.example.airticketmanager.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private int userId;
    private String username;
    private String password;
    private String sex;
    private String tel;
    private int status;
    private LocalDateTime createTime;
}
