package com.example.airticketmanager.service.impl;

import com.example.airticketmanager.entity.Flight;
import com.example.airticketmanager.mapper.AdminFlightMapper;
import com.example.airticketmanager.service.AdminFlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
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
    public int countFlights() {
        return adminFlightMapper.countFlights();
    }

    @Override
    public void insert(Flight flight) {
        adminFlightMapper.insert(flight);
    }

    /**
     * 根据航班编号获取到航班信息，并将航班信息渲染到页面
     */
    @Override
    public Flight selectByFlightNumber(String flightNumber) {
        return adminFlightMapper.selectByFlightNumber(flightNumber);
    }

    @Override
    public int updateFlight(Flight flight) {
        return adminFlightMapper.updateFlight(flight);
    }

    @Override
    public void deleteByFightId(List<Integer> flightIds) {
        for (Integer flightId:flightIds) {
            adminFlightMapper.deleteByFlightId(flightId);
        }
    }

    @Override
    public int countSelectByFlights(String flightNumber) {
        return adminFlightMapper.countSelectByFlights(flightNumber);
    }

    /**
     * 模糊匹配查询
     * @param page
     * @param size
     * @param flightNumber
     * @return
     */
    @Override
    public List<Flight> findByFlightNumber(int page, int size, String flightNumber) {
        int offset = (page - 1) * size;
        log.info("page页数{},offset的值{}",page,offset);
        return adminFlightMapper.findByFlightNumber(offset,size,flightNumber);
    }

    /**
     * 用户所需要的航班
     * @param departure
     * @param arrival
     * @param departureTime
     * @return
     */
    @Override
    public List<Flight> userNeedFlight(String departure, String arrival, LocalDate departureTime) {
        return adminFlightMapper.userNeedFlight(departure,arrival,departureTime);
    }

    /**
     * 根据id查询航班
     * @param flightId
     * @return
     */
    @Override
    public Flight selectById(Integer flightId) {
        return adminFlightMapper.selectById(flightId);
    }
}
