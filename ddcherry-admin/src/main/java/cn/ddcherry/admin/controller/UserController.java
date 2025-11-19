package cn.ddcherry.admin.controller;

import cn.ddcherry.common.result.Result;
import cn.ddcherry.system.domain.User;
import cn.ddcherry.system.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Admin-facing user controller delegating to system module.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Result<List<User>> list() {
        return Result.success(userService.listUsers());
    }
}
