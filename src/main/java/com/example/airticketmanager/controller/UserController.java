package com.example.airticketmanager.controller;

import com.example.airticketmanager.entity.User;
import com.example.airticketmanager.service.AdminUserService;
import com.example.airticketmanager.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminUserService adminUserService;
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
        adminUserService.insert(user);
        return "redirect:/user/login";
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
            return "redirect:/admin/list";
        }
        redirectAttributes.addAttribute("error","用户名或密码错误");
        return "redirect:/user/login";

    }
}
