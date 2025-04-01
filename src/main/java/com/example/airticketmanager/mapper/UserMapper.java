package com.example.airticketmanager.mapper;

import com.example.airticketmanager.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {


    /**
     * 查看用户名的唯一性
     * @param username
     * @return
     */
    @Select("select count(*) from user where username = #{username}")
    int countByUsername(@Param("username") String username);

    /**
     * 查看电话号码的唯一性
     * @param tel
     * @return
     */
    @Select("select count(*) from user where tel = #{tel}")
    int countByTel(@Param("tel") String tel);


    @Select("select password from manager where username = #{username}")
    String findUsername(String username);
}
