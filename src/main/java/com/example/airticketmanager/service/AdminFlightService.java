package com.example.airticketmanager.service;

import com.example.airticketmanager.entity.Flight;

import java.util.List;

public interface AdminFlightService {
    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    List<Flight> getFlightsByPage(int page, int size);

    int countUsers();

    void insert(Flight flight);

    Flight selectByFlightNumber(String flightNumber);

    int updateFlight(Flight flight);
}
