package com.example.airticketmanager.service;

import com.example.airticketmanager.dto.OrderDto;
import com.example.airticketmanager.entity.Order;
import com.example.airticketmanager.vo.OrderVo;

import java.util.List;

public interface AdminOrderService {
    List<OrderVo> getOrdersByPage(int page, int size);

    int countOrders();

    void addOrder(OrderDto orderDto);

    void deleteOrder(List<Integer> orderIds);

    Order selectByOrderId(Integer orderId);

    void updateOrder(OrderDto orderDto);

    List<OrderVo> findByName(int page, int size, String name);

    int countOrdersByName(String name);
}
