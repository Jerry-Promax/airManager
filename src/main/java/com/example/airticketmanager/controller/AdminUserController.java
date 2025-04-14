package com.example.airticketmanager.controller;

import com.example.airticketmanager.entity.AuditUser;
import com.example.airticketmanager.entity.User;
import com.example.airticketmanager.service.AdminUserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/admin/user")
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
        return "redirect:/admin/user/list";
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
        // 初始化搜索框空对象（首次访问时user为null）
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
        return "redirect:/admin/user/list"; // 删除后重定向到用户列表页面
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
    @Value("${file.upload-dir}")
    private String uploadDir; // 注入上传路径

    @PostMapping("/updateUser/{userId}")
    public String updateUser(
            @PathVariable("userId") int userId,
            @ModelAttribute("user") User user,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            RedirectAttributes redirectAttributes) throws IOException {

        log.info("user对象的值为{}",user);
        // 处理文件上传
        if (avatarFile != null && !avatarFile.isEmpty()) {
            // 创建上传目录
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 生成唯一文件名
            String originalFilename = avatarFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID() + fileExtension;

            // 保存文件
            Path filePath = uploadPath.resolve(newFileName);
            avatarFile.transferTo(filePath.toFile());

            // 设置相对路径（示例：/uploads/abc.jpg）
            user.setPhoto("/uploads/" + newFileName);
        }
        int result = adminUserService.updateUser(user);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("success", "更新成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "更新失败");
        }
        return "redirect:/admin/user/list";
    }
        /**
         * 根据用户名进行模糊匹配
         * @param page
         * @param size
         * @param username
         * @param model
         * @return
         */
    @GetMapping("/selectByUsername")
    public String selectByUsername(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(value = "username") String username, //@ModelAttribute绑定数据
                            Model model) {
        log.info("用户:{}",username);
        List<User> users = adminUserService.selectByUsername(username,page,size);
        log.info("用户:{}",username);
        int totalCount = adminUserService.countSelectUsers(username);
        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("size", size);
        model.addAttribute("username", username);

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        return "selectList"; // 返回视图名称
    }

    /**
     * 审核用户列表
     * @param page
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/auditUserList")
    public String auditUserList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ){
        List<User> auditUsers = adminUserService.getAuditUsersByPage(page, size);
        int totalCount = adminUserService.countAuditUsers();
        int totalPages = (int) Math.ceil((double) totalCount / size);
        model.addAttribute("users", auditUsers);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);
        return "auditUser"; // 返回视图名称
    }

    /**
     * 审核通过
     * @param auditId
     * @return
     */
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
        return "redirect:/admin/user/auditUserList";
    }

    /**
     * 审核失败
     * @param auditId
     * @return
     */
    @PostMapping("/reject")
    public String reject(@RequestParam int auditId){
        adminUserService.deleteByAuditId(auditId);
        return "redirect:/admin/user/auditUserList";
    }
}
