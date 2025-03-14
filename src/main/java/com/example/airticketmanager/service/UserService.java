package com.example.airticketmanager.service;

import com.example.airticketmanager.entity.User;

public interface UserService {
    /**
     * 查看用户名的唯一性
     * @param username
     * @return
     */
    boolean isUsernameExists(String username);

    /**
     * 查看电话号码的唯一性
     * @param tel
     * @return
     */
    boolean isTelExists(String tel);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    boolean login(String username, String password);
}
