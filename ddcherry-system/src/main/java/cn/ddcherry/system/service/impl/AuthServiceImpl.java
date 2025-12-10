package cn.ddcherry.system.service.impl;

import cn.ddcherry.common.enums.ErrorCode;
import cn.ddcherry.common.exception.ServiceException;
import cn.ddcherry.system.domain.User;
import cn.ddcherry.system.model.LoginRequest;
import cn.ddcherry.system.model.LoginResponse;
import cn.ddcherry.system.service.AuthService;
import cn.ddcherry.system.service.UserService;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Authentication service implementation backed by Sa-Token.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = Optional.ofNullable(userService.getByUsername(request.getUsername()))
                .orElseThrow(() -> new ServiceException(ErrorCode.UNAUTHORIZED.getCode(), "用户名或密码错误"));
        if (!request.getPassword().equals(user.getPassword())) {
            throw new ServiceException(ErrorCode.UNAUTHORIZED.getCode(), "用户名或密码错误");
        }
        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        LoginResponse response = new LoginResponse();
        response.setTokenName(tokenInfo.getTokenName());
        response.setTokenValue(tokenInfo.getTokenValue());
        return response;
    }

    @Override
    public User currentUser() {
        StpUtil.checkLogin();
        Long loginId = StpUtil.getLoginIdAsLong();
        return Optional.ofNullable(userService.getById(loginId))
                .orElseThrow(() -> new ServiceException(ErrorCode.UNAUTHORIZED.getCode(), "用户不存在或已被删除"));
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }
}
