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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
            adminOrderMapper.deleteLuggage(orderId);
            adminOrderMapper.deleteOrder(orderId);
        }
    }

    @Override
    public Order selectByOrderId(Integer orderId) {
        return adminOrderMapper.selectByOrderId(orderId);
    }

    @Override
    public void updateOrder(OrderDto orderDto) {
        User user = adminUserMapper.getUserIdByIdCard(orderDto.getIdCard());
        Flight flight = adminFlightMapper.getFlightIdByFlightNumber(orderDto.getFlightNumber());
        Order order = Order.builder()
                .orderId(orderDto.getOrderId())
                .userId(user.getUserId())
                .flightId(flight.getFlightId())
                .orderStatus(1)
                .seatNumber(orderDto.getSeatNumber())
                .price(flight.getPrice())
                .build();
        adminOrderMapper.updateOrder(order);
    }

    @Override
    public List<OrderVo> findByName(int page, int size, String name) {
        List<Order> orderList = adminOrderMapper.findByName(page,size,name);
        List<OrderVo> orderVoList =new ArrayList<>();
        for (Order order : orderList) {

            Flight flight = adminFlightMapper.selectById(order.getFlightId());
            User user = adminUserMapper.selectById(order.getUserId());
            OrderVo orderVo = OrderVo.builder()
                            .orderId(order.getOrderId())
                                    .name(user.getName())
                                            .idCard(user.getIdCard())
                                                    .tel(user.getTel())
                                                            .flightNumber(flight.getFlightNumber())
                                                                    .seatNumber(order.getSeatNumber())
                                                                            .price(flight.getPrice())
                                                                                    .build();
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    @Override
    public int countOrdersByName(String name) {
        return adminOrderMapper.countOrdersByName(name);
    }
}
