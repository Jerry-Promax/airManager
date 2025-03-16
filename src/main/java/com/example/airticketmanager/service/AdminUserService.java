package com.example.airticketmanager.service;

import com.example.airticketmanager.entity.AuditUser;
import com.example.airticketmanager.entity.User;

import java.util.List;

public interface AdminUserService {

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


    /**
     * 管理员修改用户
     * @param userId
     * @return
     */
    User selectById(int userId);

    int updateUser(User user);

    /**
     * 模糊匹配查询用户
     * @param username
     * @param page
     * @param size
     * @return
     */
    // Todo
    List<User> selectByUsername(String username,int page,int size);

    int countSelectUsers(String username);

    /**
     * 审核用户列表
     * @param page
     * @param size
     * @return
     */
    List<User> getAuditUsersByPage(int page, int size);

    /**
     * 插入审核表
     * @param user
     */
    void insertAudit(User user);

    /**
     * 根据审核id查找用户
     * @param auditId
     * @return
     */
    AuditUser getAuditUserById(int auditId);
    /**
     * 审核通过后根据这个id删除记录
     * @param auditId
     */
    void deleteByAuditId(int auditId);




}
