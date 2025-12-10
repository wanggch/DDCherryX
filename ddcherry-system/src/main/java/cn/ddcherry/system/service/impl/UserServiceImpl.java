package cn.ddcherry.system.service.impl;

import cn.ddcherry.framework.mybatis.datascope.DataScope;
import cn.ddcherry.system.domain.User;
import cn.ddcherry.system.mapper.UserMapper;
import cn.ddcherry.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Placeholder service implementation.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    @DataScope(deptAlias = "sys_user.create_dept", userAlias = "sys_user.create_by")
    public List<User> listUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public User getByUsername(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public User getById(Long userId) {
        return userMapper.selectById(userId);
    }
}
