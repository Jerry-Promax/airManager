package com.example.airticketmanager.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String sex;
    private String tel;
    private String name;
    private String idCard;
    private int status;
    private LocalDateTime createTime;
    private String photo;
}
