package com.example.airticketmanager.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {
    private Integer orderId;
    private Integer userId;
    private Integer flightId;
    private Integer orderStatus;
    private String seatNumber;
    private String orderTime;
    private Double price;
    private String name;
    private String idCard;
    private String tel;
    private String flightNumber;
    private String departureTime;
    private String arrivalTime;
}
