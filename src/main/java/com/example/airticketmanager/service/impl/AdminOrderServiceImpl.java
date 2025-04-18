package com.example.airticketmanager.service.impl;

import com.example.airticketmanager.dto.OrderDto;
import com.example.airticketmanager.entity.Flight;
import com.example.airticketmanager.entity.Order;
import com.example.airticketmanager.entity.User;
import com.example.airticketmanager.mapper.AdminFlightMapper;
import com.example.airticketmanager.mapper.AdminOrderMapper;
import com.example.airticketmanager.mapper.AdminUserMapper;
import com.example.airticketmanager.service.AdminOrderService;
import com.example.airticketmanager.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {
    @Autowired
    private AdminOrderMapper adminOrderMapper;
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private AdminFlightMapper adminFlightMapper;
    @Override
    public List<OrderVo> getOrdersByPage(int page, int size) {
        int offset = (page-1)*size;
        adminOrderMapper.selectOrdersByPage(offset, size);
        return adminOrderMapper.selectOrdersByPage(offset,size);
    }

    @Override
    public int countOrders() {
        return adminOrderMapper.countOrders();
    }

    @Override
    public void addOrder(OrderDto orderDto) {
        User user = adminUserMapper.getUserIdByIdCard(orderDto.getIdCard());
        Flight flight = adminFlightMapper.getFlightIdByFlightNumber(orderDto.getFlightNumber());
        Order order = Order.builder()
                        .userId(user.getUserId())
                                .flightId(flight.getFlightId())
                                        .orderStatus(1)
                                                .seatNumber(orderDto.getSeatNumber())
                                                        .orderTime(LocalDateTime.now())
                                                                .price(flight.getPrice())
                                                                        .build();
        adminOrderMapper.addOrder(order);
    }

    @Override
    public void deleteOrder(List<Integer> orderIds) {
        for (Integer orderId:orderIds) {
            adminOrderMapper.deleteOrder(orderId);
        }
    }

    @Override
    public Order selectByOrderId(Integer orderId) {
        return adminOrderMapper.selectByOrderId(orderId);
    }

    @Override
    public void updateOrder(OrderDto orderDto) {
        adminOrderMapper.updateOrder(orderDto);
    }
}
