package com.example.airticketmanager.service;

import com.example.airticketmanager.dto.OrderDto;
import com.example.airticketmanager.entity.Order;
import com.example.airticketmanager.vo.orderVo;

import java.util.List;

public interface AdminOrderService {
    List<orderVo> getOrdersByPage(int page, int size);

    int countOrders();

    void addOrder(OrderDto orderDto);

    void deleteOrder(List<Integer> orderIds);
}
