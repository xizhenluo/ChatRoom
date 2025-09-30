package com.lxz.chatroom.common.user.service.impl;

import com.lxz.chatroom.common.common.constant.RedisKey;
import com.lxz.chatroom.common.common.utils.JwtUtils;
import com.lxz.chatroom.common.common.utils.RedisUtils;
import com.lxz.chatroom.common.user.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/9/29
 */
@Service
public class LoginServiceImpl implements LoginService {
    private static final int TOKEN_EXPIRE_DAYS = 3;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean verify(String token) {
        return false;
    }

    @Override
    public void renewalTokenIfNecessary(String token) {

    }

    @Override
    public String login(Long uid) {
        String token = jwtUtils.createToken(uid);
        // use redis to store
        RedisUtils.set(getUserTokenKey(uid), token, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        return token;
    }

    /**
     * return uid if token is valid
     * @param token
     * @return
     */
    @Override
    public Long getValidUid(String token) {
        // decode the uid from the token
        Long uid = jwtUtils.getUidOrNull(token);
        if (Objects.isNull(uid)) {
            return null;
        }
        // inquire token from radis according to uid
        String tokenInRedis = RedisUtils.get(getUserTokenKey(uid));
        // if exists, token is valid, return uid
        if (StringUtils.isBlank(tokenInRedis)) {
            return null;
        }
        return uid;
    }

    private String getUserTokenKey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
    }
}
