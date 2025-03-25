package com.example.airticketmanager.service.impl;

import com.example.airticketmanager.entity.AuditUser;
import com.example.airticketmanager.entity.User;
import com.example.airticketmanager.mapper.AdminUserMapper;
import com.example.airticketmanager.mapper.AuditMapper;
import com.example.airticketmanager.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private AuditMapper auditMapper;

    /**
     * 新增员工
     * @param user
     * @return
     */
    @Override
    public void insert(User user) {
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        adminUserMapper.insert(user);
    }

    /**
     * 删除员工
     * @param uids
     */
    @Override
    public void deleteById(List<Integer> uids) {
        for (int uid:
             uids) {
            adminUserMapper.deleteById(uid);
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
        return adminUserMapper.selectUsersByPage(offset, size);
    }
    @Override
    public int countUsers() {
        return adminUserMapper.countUsers();
    }

    @Override
    public User selectById(int userId) {
        return adminUserMapper.selectById(userId);
    }

    @Override
    public int updateUser(User user) {
        return adminUserMapper.updateUser(user);
    }

    @Override
    public List<User> selectByUsername(String username, int page, int size) {
        int offset = (page - 1) * size;
        return adminUserMapper.selectByUsername(username,offset,size);
    }

    @Override
    public int countSelectUsers(String username) {
        return adminUserMapper.countSelectUsers(username);
    }

    @Override
    public List<User> getAuditUsersByPage(int page, int size) {
        int offset = (page - 1) * size;
        return adminUserMapper.selectAuditUsersByPage(offset, size);
    }

    /**
     * 插入审核表
     * @param user
     */
    @Override
    public void insertAudit(User user) {
        auditMapper.insertAudit(user);
    }

    /**
     * 根据审核id查找用户
     * @param auditId
     * @return
     */
    @Override
    public AuditUser getAuditUserById(int auditId) {
        return auditMapper.getAuditUserById(auditId);
    }

    @Override
    public void deleteByAuditId(int auditId) {
        auditMapper.deleteByAuditId(auditId);
    }


}
