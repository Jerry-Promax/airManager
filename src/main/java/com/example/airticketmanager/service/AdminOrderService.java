package com.example.airticketmanager.service;

import com.example.airticketmanager.entity.Order;

import java.util.List;

public interface AdminOrderService {
    List<Order> getOrdersByPage(int page, int size);

    int countOrders();
}
