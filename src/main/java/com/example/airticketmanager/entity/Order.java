package com.example.airticketmanager.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
//@AllArgsConstructor(access = AccessLevel.PUBLIC) // 因为用了builder注解之后，生成的包名默认是私有级，开启这个关键注解会变为public

public class Order {
    private Integer orderId;
    private Integer userId;
    private Integer flightId;
    private Integer orderStatus;
    private String seatNumber;
    private LocalDateTime orderTime;
    private Double price;

}
