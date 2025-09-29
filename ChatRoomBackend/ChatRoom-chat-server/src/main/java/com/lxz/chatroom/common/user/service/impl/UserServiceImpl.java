package com.lxz.chatroom.common.user.service.impl;

import com.lxz.chatroom.common.user.dao.UserDao;
import com.lxz.chatroom.common.user.domain.entity.User;
import com.lxz.chatroom.common.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/9/29
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional // ensure that the saving and events belong to one transaction
    public Long register(User insertUser) {
        userDao.save(insertUser);
        // todo register events
        return insertUser.getId();
    }
}
