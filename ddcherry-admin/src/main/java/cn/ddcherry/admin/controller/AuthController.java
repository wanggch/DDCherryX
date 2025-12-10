package cn.ddcherry.admin.controller;

import cn.ddcherry.common.result.Result;
import cn.ddcherry.system.domain.User;
import cn.ddcherry.system.model.LoginRequest;
import cn.ddcherry.system.model.LoginResponse;
import cn.ddcherry.system.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication endpoints powered by Sa-Token.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 登录接口：校验用户信息后返回 Token。
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return Result.success(authService.login(request));
    }

    /**
     * 获取当前登录用户信息。
     */
    @GetMapping("/me")
    public Result<User> currentUser() {
        return Result.success(authService.currentUser());
    }

    /**
     * 退出登录，清理会话。
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        authService.logout();
        return Result.success(null);
    }
}
