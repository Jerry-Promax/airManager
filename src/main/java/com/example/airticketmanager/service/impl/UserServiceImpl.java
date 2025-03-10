package com.example.airticketmanager.service.impl;

import com.example.airticketmanager.entity.User;
import com.example.airticketmanager.mapper.UserMapper;
import com.example.airticketmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 新增员工
     * @param user
     * @return
     */
    @Override
    public void insert(User user) {
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    /**
     * 删除员工
     * @param uids
     */
    @Override
    public void deleteById(List<Integer> uids) {
        for (int uid:
             uids) {
            userMapper.deleteById(uid);
        }
    }

    /**
     * 分页查询用户
     *
     * @param page 当前页码
     * @param size 每页大小
     * @return 分页结果
     */
    @Override
    public List<User> getUsersByPage(int page, int size) {
        int offset = (page - 1) * size;
        return userMapper.selectUsersByPage(offset, size);
    }
    @Override
    public int countUsers() {
        return userMapper.countUsers();
    }

    @Override
    public User selectById(int userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public List<User> selectByUsername(String username, int page, int size) {
        int offset = (page - 1) * size;
        return userMapper.selectByUsername(username,offset,size);
    }

    @Override
    public int countSelectUsers(String username) {
        return userMapper.countSelectUsers();
    }


}
