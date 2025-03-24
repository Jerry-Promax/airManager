package com.example.airticketmanager.controller;

import com.example.airticketmanager.entity.Flight;
import com.example.airticketmanager.entity.User;
import com.example.airticketmanager.service.AdminFlightService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/flight")
@Slf4j
public class AdminFlightController {
    @Autowired
    private AdminFlightService adminFlightService;

    /**
     * 分页查询航班
     * @param page
     * @param size
     * @param flight
     * @param httpSession
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @ModelAttribute("flight") Flight flight,
                       HttpSession httpSession,
                       Model model){
        // 若未登录则直接返回login页面
//        if (httpSession.getAttribute("username") == null) {
//
//            return "redirect:/user/login";
//        }
        // 初始化搜索框空对象（首次访问时user为null）
        if (flight == null) {
            flight = new Flight();
            model.addAttribute("flight", flight);
        }
        // 判断是否是搜索请求
        if (StringUtils.hasText(flight.getFlightNumber())) {
            return "redirect:/admin/flight/selectByFlightNumber?username=" + flight.getFlightNumber()
                    + "&page=" + page + "&size=" + size;
        }
        List<Flight> flightList = adminFlightService.getFlightsByPage(page, size);
        int totalCount = adminFlightService.countUsers();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        model.addAttribute("flightList", flightList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        return "flightList"; // 返回视图名称
    }

    @GetMapping("/addFlight")
    public String addFlightPage(){
        return "addFlight";
    }

    /**
     * 新增航班
     * @param flight
     * @return
     */
    @PostMapping("/addFlight")
    public String addFlight(@ModelAttribute Flight flight){
        adminFlightService.insert(flight);
        return "redirect:/admin/flight/list";
    }

    /**
     * 根据航班编号获取到航班信息，并将航班信息渲染到页面
     */
    @GetMapping("/updateFlight/{flightNumber}")
    public String showEditForm(@PathVariable("flightNumber") String flightNumber,
                               Model model) {
        log.info("航班编号为：{}",flightNumber);
        Flight flight = adminFlightService.selectByFlightNumber(flightNumber);
        model.addAttribute("flight", flight);
        return "updateFlight"; // 编辑页面视图名称
    }

    /**
     * 修改航班信息
     * @param flight
     * @return
     */
    @PostMapping("/updateFlight/{flightNumber}")
    public String updateFlight(@PathVariable String flightNumber,
                               @ModelAttribute("flight") Flight flight){
        flight.setFlightNumber(flightNumber);
        int n = adminFlightService.updateFlight(flight);
        if (n > 0) {
            log.info("修改成功");
        } else {
            log.info("修改失败");
        }
        return "redirect:/admin/flight/list";
    }
}
