package com.example.airticketmanager.mapper;

import com.example.airticketmanager.entity.Flight;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AdminFlightMapper {

    @Results({
            @Result(property = "flightId", column = "flight_id"),
            @Result(property = "flightNumber", column = "flight_number"),
            @Result(property = "departureTime", column = "departure_time"),
            @Result(property = "arrivalTime", column = "arrival_time"),
            @Result(property = "departure", column = "departure"),
            @Result(property = "arrival", column = "arrival"),
            @Result(property = "totalSeats", column = "total_seats"),
            @Result(property = "availableSeats", column = "available_seats")
    })

    /**
     * 分页查询
     * @param offset
     * @param limit
     * @return
     */
    List<Flight> selectFlightsByPage(@Param("offset") int offset, @Param("limit") int limit);

    int countFlights();

    /**
     * 增加航班
     * @param flight
     */
    @Insert("INSERT INTO flights (flight_number, departure_time, arrival_time, departure, arrival, total_seats, available_seats) VALUES" +
            "(#{flightNumber},#{departureTime},#{arrivalTime},#{departure},#{arrival},#{totalSeats},#{availableSeats})")
    void insert(Flight flight);

    @Select("select * from flights where flight_number = #{flightNumber}")
    Flight selectByFlightNumber(String flightNumber);

    @Update("UPDATE flights SET " +
            "departure_time = #{departureTime}, " +
            "arrival_time = #{arrivalTime}, " +
            "departure = #{departure}, " +
            "arrival = #{arrival}, " +
            "total_seats = #{totalSeats}, " +
            "available_seats = #{availableSeats} " +
            "WHERE flight_number = #{flightNumber}")
    int updateFlight(Flight flight);


    @Delete("delete from flights where flight_id = #{flightId};")
    void deleteByFlightId(Integer flightId);

    @Select("select count(*) from flights where flight_number LIKE CONCAT('%',#{flightNumber},'%')")
    int countSelectByFlights(String flightNumber);

    /**
     * 搜索框查找模糊匹配
     * @param offset
     * @param size
     * @param flightNumber
     * @return
     */
    @Select("select * from flights where flight_number LIKE CONCAT('%',#{flightNumber},'%') limit #{size} offset #{offset};")
    List<Flight> findByFlightNumber(@Param("offset") int offset, @Param("size") int size, @Param("flightNumber") String flightNumber);

    /**
     * 用户需要的航班
     * @param departure
     * @param arrival
     * @param departureTime
     * @return
     */

    List<Flight> userNeedFlight(@Param("departure") String departure,@Param("arrival") String arrival,@Param("departureTime") LocalDate departureTime);

    @Select("select * from flights where flight_number = #{flightNumber}")
    Flight getFlightIdByFlightNumber(String flightNumber);

    @Select("select * from flights where flight_id = #{flightId}")
    Flight selectById(Integer flightId);
}
