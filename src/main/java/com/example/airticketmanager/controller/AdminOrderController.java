package com.example.airticketmanager.controller;

import com.example.airticketmanager.dto.OrderDto;
import com.example.airticketmanager.service.AdminOrderService;
import com.example.airticketmanager.vo.orderVo;
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
//        if (httpSession.getAttribute("username") == null) {
//            return "redirect:/user/login";
//        }
        List<orderVo> orderList = adminOrderService.getOrdersByPage(page, size);
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
}



