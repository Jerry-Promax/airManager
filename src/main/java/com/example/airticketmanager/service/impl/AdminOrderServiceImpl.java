package com.example.airticketmanager.service.impl;

import com.example.airticketmanager.entity.Order;
import com.example.airticketmanager.mapper.AdminOrderMapper;
import com.example.airticketmanager.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {
    @Autowired
    private AdminOrderMapper adminOrderMapper;
    @Override
    public List<Order> getOrdersByPage(int page, int size) {
        int offset = (page-1)*size;
        return adminOrderMapper.selectOrdersByPage(offset,size);
    }

    @Override
    public int countOrders() {
        return adminOrderMapper.countOrders();
    }
}
