package com.example.airticketmanager.controller;

import com.example.airticketmanager.entity.Flight;
import com.example.airticketmanager.entity.Order;
import com.example.airticketmanager.service.AdminOrderService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
@Slf4j
public class AdminOrderController {
    @Autowired
    private AdminOrderService adminOrderService;

    /**
     * 分页查询
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
//        if (httpSession.getAttribute("username") == null) {
//            return "redirect:/user/login";
//        }
        List<Order> orderList = adminOrderService.getOrdersByPage(page, size);
        log.info("order内容为：{}",orderList);
        int totalCount = adminOrderService.countOrders();
        int totalPages = (int) Math.ceil((double) totalCount/size);
        model.addAttribute("orderList", orderList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        return "orderList"; // 返回视图名称
    }
}
