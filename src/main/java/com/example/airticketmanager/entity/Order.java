package com.example.airticketmanager.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private Integer orderId;
    private Integer uid;
    private Integer fid;
    private Integer orderStatus;
    private String seatNumber;
    private LocalDateTime orderTime;
    private Double price;
    private Flight flight;
    private User user;
}
