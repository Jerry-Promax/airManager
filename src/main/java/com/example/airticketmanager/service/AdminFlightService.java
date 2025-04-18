package com.example.airticketmanager.service;

import com.example.airticketmanager.entity.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AdminFlightService {
    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    List<Flight> getFlightsByPage(int page, int size);

    int countFlights();

    void insert(Flight flight);

    Flight selectByFlightNumber(String flightNumber);

    int updateFlight(Flight flight);

    void deleteByFightId(List<Integer> flightIds);

    int countSelectByFlights(String flightNumber);

    List<Flight> findByFlightNumber(int page, int size, String flightNumber);

    List<Flight> userNeedFlight(String departure, String arrival, LocalDate departureTime);

    Flight selectById(Integer flightId);
}
