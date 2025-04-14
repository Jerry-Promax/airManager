package com.example.airticketmanager.vo;

import lombok.Data;

@Data
public class orderVo {
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
}
