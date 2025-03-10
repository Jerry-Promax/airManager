package com.example.airticketmanager.service;

import com.example.airticketmanager.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 新增员工
     * @param user
     * @return
     */
    void insert(User user);

    /**
     * 删除员工
     * @param uids
     */
    void deleteById(List<Integer> uids);

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    List<User> getUsersByPage(int page, int size);

    int countUsers();


    User selectById(int userId);

    int updateUser(User user);
    // Todo
    List<User> selectByUsername(String username,int page,int size);

    int countSelectUsers(String username);
}
