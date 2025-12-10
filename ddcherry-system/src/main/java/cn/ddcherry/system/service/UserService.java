package cn.ddcherry.system.service;

import cn.ddcherry.system.domain.User;

import java.util.List;

/**
 * Placeholder service interface.
 */
public interface UserService {
    List<User> listUsers();

    /**
     * Query user by username.
     *
     * @param username login name
     * @return matched user or {@code null}
     */
    User getByUsername(String username);

    /**
     * Query user by id.
     *
     * @param userId primary key
     * @return matched user or {@code null}
     */
    User getById(Long userId);
}
