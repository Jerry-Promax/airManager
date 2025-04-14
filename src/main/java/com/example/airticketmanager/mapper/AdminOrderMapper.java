package com.example.airticketmanager.mapper;

import com.example.airticketmanager.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminOrderMapper {

//    @Select("SELECT * FROM orders LIMIT #{limit} OFFSET #{offset}")
    List<Order> selectOrdersByPage(@Param("offset") int offset, @Param("limit") int limit);

    @Select("SELECT COUNT(*) FROM orders")
    int countOrders();
}
