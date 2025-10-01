package com.lxz.chatroom.common.user.service.impl;

import com.lxz.chatroom.common.common.constant.RedisKey;
import com.lxz.chatroom.common.common.utils.JwtUtils;
import com.lxz.chatroom.common.common.utils.RedisUtils;
import com.lxz.chatroom.common.user.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    private static final int TOKEN_RENEWAL_DAYS = 1;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean verify(String token) {
        return false;
    }

    /**
     * asynchronized renewal
     * renew the expire day of a token stored in redis
     * @param token
     */
    @Override
    @Async // need to designate the thread-pool manually, or it will use default one
    // when using @Async, we need to implement AsyncConfigurer and override the getAsyncExecutor() method to generate our thread pool
    // otherwise, it will call default implementation
    public void renewalTokenIfNecessary(String token) {
        Long uid = getValidUid(token);
        String userTokenKey = getUserTokenKey(uid);
        Long expireDays = RedisUtils.getExpire(userTokenKey, TimeUnit.DAYS);
        if (expireDays == -2) return; // -2 means invalid key
        if (expireDays < TOKEN_RENEWAL_DAYS) {
            RedisUtils.expire(userTokenKey, TOKEN_EXPIRE_DAYS, TimeUnit.DAYS);
        }
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
        String tokenInRedis = RedisUtils.getStr(getUserTokenKey(uid)); // use getStr() instead of get() because underlying code helps to process the string
        // if exists, token is valid, return uid
        if (StringUtils.isBlank(tokenInRedis)) {
            return null;
        }
        // in case token is refreshed in other devices and stored in redis, but we can still get uid from the expired/depricated token
        return Objects.equals(tokenInRedis, token) ? uid : null;
    }

    private String getUserTokenKey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_TOKEN_STRING, uid);
    }
}
