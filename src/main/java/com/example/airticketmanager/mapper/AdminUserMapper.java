package com.example.airticketmanager.mapper;

import com.example.airticketmanager.entity.AuditUser;
import com.example.airticketmanager.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminUserMapper {

    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "tel", column = "tel"),
            @Result(property = "status", column = "status"),
            @Result(property = "name",column = "name"),
            @Result(property = "idCard",column = "id_card"),
    })

    @Insert("insert into user (username,password,sex,tel,status,create_time,name,id_card) " +
            "values" +
            " (#{username},#{password},#{sex},#{tel},#{status},NOW(),#{name},#{idCard})")
    void insert(User user);


    @Delete("delete from user where user_id = #{uid}")
    void deleteById(int uid);
    /**
     * 分页查询用户
     * @param offset 偏移量
     * @param limit  每页大小
     * @return 用户列表
     */

    List<User> selectUsersByPage(@Param("offset") int offset, @Param("limit") int limit);
    int countUsers();

    @Select("select * from user where user_id= #{userId}")
    User selectById(int userId);

    int updateUser(User user);

    List<User> selectByUsername(@Param("username") String username,@Param("offset") int offset, @Param("limit") int limit);

    int countSelectUsers(String username);

    List<User> selectAuditUsersByPage(@Param("offset") int offset, @Param("limit") int limit);


    @Select("select * from user where id_card = #{idCard}")
    User getUserIdByIdCard(String idCard);

    @Update("update user set password = #{pwd} where user_id = #{userId}")
    void reset(@Param("userId") int userId,@Param("pwd") String pwd);
}
