package com.example.airticketmanager.service.impl;

import com.example.airticketmanager.entity.Flight;
import com.example.airticketmanager.mapper.AdminFlightMapper;
import com.example.airticketmanager.service.AdminFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminFlightServiceImpl implements AdminFlightService {
    @Autowired
    private AdminFlightMapper adminFlightMapper;
    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<Flight> getFlightsByPage(int page, int size) {
        int offset = (page - 1) * size;
        return adminFlightMapper.selectFlightsByPage(offset, size);
    }

    @Override
    public int countUsers() {
        return adminFlightMapper.countFlights();
    }

    @Override
    public void insert(Flight flight) {
        adminFlightMapper.insert(flight);
    }

    @Override
    public Flight selectByFlightNumber(String flightNumber) {
        return adminFlightMapper.selectByFlightNumber(flightNumber);
    }

    @Override
    public int updateFlight(Flight flight) {
        return adminFlightMapper.updateFlight(flight);
    }
}
