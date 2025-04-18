package com.example.airticketmanager.controller;

import com.example.airticketmanager.dto.OrderDto;
import com.example.airticketmanager.entity.Flight;
import com.example.airticketmanager.entity.Order;
import com.example.airticketmanager.entity.User;
import com.example.airticketmanager.service.AdminFlightService;
import com.example.airticketmanager.service.AdminOrderService;
import com.example.airticketmanager.service.AdminUserService;
import com.example.airticketmanager.vo.OrderVo;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
@Slf4j
public class AdminOrderController {
    @Autowired
    private AdminOrderService adminOrderService;
    @Autowired
    private AdminFlightService adminFlightService;
    @Autowired
    private AdminUserService adminUserService;

    /**
     * 分页查询
     * @param page
     * @param size
     * @param httpSession
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size,
                       HttpSession httpSession,
                       Model model){
        if (httpSession.getAttribute("username") == null) {
            return "redirect:/user/login";
        }
        List<OrderVo> orderList = adminOrderService.getOrdersByPage(page, size);
        log.info("order内容为：{}",orderList);
        int totalCount = adminOrderService.countOrders();
        int totalPages = (int) Math.ceil((double) totalCount/size);
        model.addAttribute("orderList", orderList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        return "orderList"; // 返回视图名称
    }

    @GetMapping("/addOrder")
    public String addOrderPage(){
        return "addOrder";
    }

    /**
     * 增
     * @param orderDto
     * @return
     */
    @PostMapping("/addOrder")
    public String addOrder(@ModelAttribute OrderDto orderDto){
        log.info("order属性值{}",orderDto);
        adminOrderService.addOrder(orderDto);
        return "redirect:/admin/order/list";
    }

    /**
     * 删
     * @param orderIds
     * @return
     */
    @PostMapping("/deleteOrder")
    public String deleteOrder(@RequestParam List<Integer> orderIds){
        adminOrderService.deleteOrder(orderIds);
        return "redirect:/admin/order/list";
    }

    /**
     * 获取改的order
     * @param orderId
     * @param model
     * @return
     */
    @GetMapping("/updateOrder")
    public String showOrderForm(@RequestParam("orderId") Integer orderId,
                                Model model){
        Order order = adminOrderService.selectByOrderId(orderId);
        User user = adminUserService.selectById(order.getUserId());
        Flight flight = adminFlightService.selectById(order.getFlightId());
        OrderVo orderVo = OrderVo.builder()
                .orderId(orderId)
                    .name(user.getName())
                        .idCard(user.getIdCard())
                            .tel(user.getTel())
                                .flightNumber(flight.getFlightNumber())
                                    .seatNumber(order.getSeatNumber())
                                        .build();
        model.addAttribute("order",orderVo);
        return "updateOrder";
    }

    /**
     * 修改信息
     * @param orderDto
     * @return
     */
    // Todo 目前只能修改座位号，其他信息没法修改，因为涉及到其他表，多表操作后续在做。可以先判断orderDto传的信息是否有变更，通过if条件判断，然
    //  后若有变更，就查询修改后的userId和flightId，然后new一个order对象，把值全部copy过去，最后将order对象传给mapper层进行修改。
    @PostMapping("/updateOrder")
    public String updateOrder(@ModelAttribute("order") OrderDto orderDto){
        log.info("前端传递的值{}",orderDto);
        adminOrderService.updateOrder(orderDto);
        return "redirect:/admin/order/list";
    }
}



