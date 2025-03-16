package com.example.airticketmanager.controller;

import com.example.airticketmanager.entity.AuditUser;
import com.example.airticketmanager.entity.User;
import com.example.airticketmanager.service.AdminUserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;
    /**
     * 返回添加用户页面
     * @return
     */
    @GetMapping("/addUser")
    public String addUserPage() {
        log.info("增加用户界面");
        return "addUser";
    }
    /**
     * 新增员工
     * @param user
     */
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user){ //表单用@ModelAttribute @RequstBody适用于ajax请求
        log.info("新增员工");
        adminUserService.insert(user);
        return "redirect:/admin/list";
    }

    /**
     * 分页查询用户
     * @param page 当前页码
     * @param size 每页大小
     * @return 分页结果
     */
    @GetMapping("/list")
    public String listUsers(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @ModelAttribute("user") User user, // 接收查询参数
                            HttpSession httpSession,
                            Model model) {
        // 若未登录则直接返回login页面
        if (httpSession.getAttribute("username") == null) {
            return "redirect:/user/login";
        }
        // 初始化空对象（首次访问时user为null）
        if (user == null) {
            user = new User();
            model.addAttribute("user", user);
        }
        // 判断是否是搜索请求
        if (StringUtils.hasText(user.getUsername())) {
            return "redirect:/admin/selectByUsername?username=" + user.getUsername()
                    + "&page=" + page + "&size=" + size;
        }

        List<User> users = adminUserService.getUsersByPage(page, size);
        int totalCount = adminUserService.countUsers();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        return "list"; // 返回视图名称
    }
    /**
     * 删除员工
     * @param uids
     */
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam List<Integer> uids) {
        log.info("删除员工id为: {}", uids);
        adminUserService.deleteById(uids);
        return "redirect:/admin/list"; // 删除后重定向到用户列表页面
    }
    /**
     * 根据id获取到用户信息，并将用户信息渲染到页面
     */
    @GetMapping("/updateUser/{userId}")
    public String showEditForm(@PathVariable("userId") int userId,
                               Model model) {
        User user = adminUserService.selectById(userId);
        model.addAttribute("user", user);
        return "updateUser"; // 编辑页面视图名称
    }
    /**
     * 修改信息
     * @param user
     */
    @PostMapping("/updateUser/{userId}")
    public String updateUser(@PathVariable("userId") int userId,
                             @ModelAttribute("user") User user) {
//        user.setUserId(userId);
        int updateResult = adminUserService.updateUser(user);
        if (updateResult > 0) {
           log.info("修改成功");
        } else {
            log.info("修改失败");
        }
        return "redirect:/admin/list"; // 返回用户列表页
    }

    /**
     * 根据用户名进行模糊匹配
     * @param page
     * @param size
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/selectByUsername")
    public String selectByUsername(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @ModelAttribute("user") User user, //@ModelAttribute绑定数据
                            Model model) {
        log.info("用户:{}",user);
        List<User> users = adminUserService.selectByUsername(user.getUsername(),page,size);
        log.info("用户:{}",user);
        int totalCount = adminUserService.countSelectUsers(user.getUsername());
        int totalPages = (int) Math.ceil((double) totalCount / size);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        return "selectList"; // 返回视图名称
    }

    @GetMapping("/auditUserList")
    public String auditUserList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ){
        List<User> auditUsers = adminUserService.getAuditUsersByPage(page, size);
        int totalCount = adminUserService.countUsers();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        model.addAttribute("users", auditUsers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        return "auditUser"; // 返回视图名称
    }

    @PostMapping("/auditPass")
    public String audit(@RequestParam int auditId){

        AuditUser auditUser = adminUserService.getAuditUserById(auditId);
        User user = new User();
        user.setUsername(auditUser.getUsername());
        user.setPassword(auditUser.getPassword());
        user.setSex(auditUser.getSex());
        user.setTel(auditUser.getTel());
        adminUserService.insert(user);
        adminUserService.deleteByAuditId(auditId);
        return "redirect:/admin/auditUserList";
    }

    @PostMapping("/reject")
    public String reject(@RequestParam int auditId){
        adminUserService.deleteByAuditId(auditId);
        return "redirect:/admin/auditUserList";
    }
}
