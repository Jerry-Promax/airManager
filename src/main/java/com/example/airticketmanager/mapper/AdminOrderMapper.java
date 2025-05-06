package com.example.airticketmanager.mapper;

import com.example.airticketmanager.dto.OrderDto;
import com.example.airticketmanager.entity.Order;
import com.example.airticketmanager.vo.OrderVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminOrderMapper {

//    @Select("SELECT * FROM orders LIMIT #{limit} OFFSET #{offset}")
    List<OrderVo> selectOrdersByPage(@Param("offset") int offset, @Param("limit") int limit);

    @Select("SELECT COUNT(*) FROM orders")
    int countOrders();

    @Insert("insert into orders (user_id,flight_id,order_status,seat_number,order_time,price)" +
            "values " +
            "(#{userId},#{flightId},#{orderStatus},#{seatNumber},#{orderTime},#{price})")
    void addOrder(Order order);

    @Delete("delete from orders where order_id = #{orderId}")
    void deleteOrder(Integer orderId);

    @Select("select * from orders where order_id = #{orderId}")
    Order selectByOrderId(Integer orderId);

    void updateOrder(Order order);

    @Select(  "SELECT o.* " +
            "FROM orders o " +
            "JOIN user u ON o.user_id = u.user_id " +
            "WHERE u.name = #{name}")
    List<Order> findByName(int page, int size, String name);

    @Select("SELECT count(*) " +
            "FROM orders o " +
            "JOIN user u ON o.user_id = u.user_id " +
            "WHERE u.name = #{name}")
    int countOrdersByName(String name);

    @Delete("delete from luggage where order_id = #{orderId}")
    void deleteLuggage(Integer orderId);
}
