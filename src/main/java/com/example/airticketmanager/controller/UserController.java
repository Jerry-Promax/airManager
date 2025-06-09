package com.example.airticketmanager.controller;

import com.example.airticketmanager.entity.Flight;
import com.example.airticketmanager.entity.User;
import com.example.airticketmanager.service.AdminFlightService;
import com.example.airticketmanager.service.AdminUserService;
import com.example.airticketmanager.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private AdminFlightService adminFlightService;
    /**
     * 返回注册页面
     * @return
     */
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User()); // 初始化空对象
        log.info("注册界面");
        return "register";
    }

    /**
     * 注册功能
     * @param user
//     * @param result
     * @param model
     * @return
     */
    @PostMapping("/register")
    public String register(
            @ModelAttribute("user") User user,
//            BindingResult result,
            Model model) {
        log.info("开始注册");
        if (userService.isUsernameExists(user.getUsername())) {
//            model.addAttribute("error", "用户名已存在");
            log.info("注册失败因为用户名重复");
            return "register";
        }
        // 检查电话号码只可绑定一个用户
        if (userService.isTelExists(user.getTel())) {
//            model.addAttribute("error", "电话号码已存在");
            log.info("注册失败因为电话号码已存在");
            return "register";
        }
        log.info("开始插入用户");
        adminUserService.insertAudit(user);
        // Todo 跳转到提示框待审核，从提示框跳转到登录界面
        return "redirect:/user/tap";
    }

    /**
     * 提示页
     * @return
     */
    @GetMapping("/tap")
    public String tap(){
        return "tap";
    }
    /**
     * 返回登录页
     * @return
     */
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    /**
     * 登录功能
     * @return
     */
    // Todo 后续登录功能使用jwt令牌来做，以及密码的加密
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession httpSession,
                        RedirectAttributes redirectAttributes){
        if (userService.login(username,password)){
            httpSession.setAttribute("username",username);
            return "redirect:/admin/user/list";
        }
        redirectAttributes.addAttribute("error","用户名或密码错误");
        return "redirect:/user/login";

    }

    // 可以忽视，另一个端口号已实现
    @GetMapping("/index")
    public String indexPage(){
        return "index";
    }
    @GetMapping("/queryFlightList")
    public String query(String departure,
                        String arrival,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureTime,
                        Model model){
        log.info("时间为{},地点为{}",departureTime,departure);
        List<Flight> flightList = adminFlightService.userNeedFlight(departure,arrival,departureTime);
        model.addAttribute("flightList",flightList);
        return "queryFlightList";
    }

}
