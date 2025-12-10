package cn.ddcherry.system.service;

import cn.ddcherry.system.domain.User;
import cn.ddcherry.system.model.LoginRequest;
import cn.ddcherry.system.model.LoginResponse;

/**
 * Authentication service leveraging Sa-Token.
 */
public interface AuthService {

    /**
     * Perform user login and build token response.
     *
     * @param request login payload
     * @return token info
     */
    LoginResponse login(LoginRequest request);

    /**
     * Fetch the currently logged-in user.
     *
     * @return user info bound to current session
     */
    User currentUser();

    /**
     * Logout current session.
     */
    void logout();
}
