package com.example.airticketmanager.mapper;

import com.example.airticketmanager.entity.AuditUser;
import com.example.airticketmanager.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AuditMapper {
//    @Results(id = "userResultMap", value = {
//            @Result(property = "auditId", column = "audit_id"),
//            @Result(property = "username", column = "username"),
//            @Result(property = "password", column = "password"),
//            @Result(property = "sex", column = "sex"),
//            @Result(property = "tel", column = "tel")
//    })

    @Insert("insert into auditUser (username,password,sex,tel) values (#{username},#{password},#{sex},#{tel})")
    void insertAudit(User user);

    @Select("select * from auditUser where audit_id=#{auditId}")
    AuditUser getAuditUserById(int auditId);

    @Delete("delete from auditUser where audit_id=#{auditId}")
    void deleteByAuditId(int auditId);

    @Select("select count(*) from auditUser")
    int countAuditUsers();
}
