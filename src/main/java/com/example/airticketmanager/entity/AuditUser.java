package com.example.airticketmanager.entity;

import lombok.Data;

@Data
public class AuditUser {
    private int auditId;
    private String username;
    private String password;
    private String sex;
    private String tel;
    private String name;
    private String idCard;
}
