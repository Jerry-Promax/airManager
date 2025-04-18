package com.example.airticketmanager.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Integer orderId;
    private String name;
    private String idCard;
    private String tel;
    private String flightNumber;
    private String seatNumber;
}
