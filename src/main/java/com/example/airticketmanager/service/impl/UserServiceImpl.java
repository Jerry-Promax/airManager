package com.example.airticketmanager.service.impl;

import com.example.airticketmanager.entity.User;
import com.example.airticketmanager.mapper.UserMapper;
import com.example.airticketmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean isUsernameExists(String username) {
        log.info("用户信息{}",username);
        return userMapper.countByUsername(username) > 0;
    }

    @Override
    public boolean isTelExists(String tel) {
        return userMapper.countByTel(tel) > 0;
    }

    @Override
    public boolean login(String username, String password) {
        return password.equals(userMapper.findUsername(username));
    }
}
