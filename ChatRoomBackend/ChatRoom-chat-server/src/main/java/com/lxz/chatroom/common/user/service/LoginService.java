package com.lxz.chatroom.common.user.service;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/9/29
 */
public interface LoginService {
    /**
     * verify if token is valid
     *
     * @param token
     * @return
     */
    boolean verify(String token);

    /**
     * renew the token
     *
     * @param token
     */
    void renewalTokenIfNecessary(String token);

    /**
     * get token after login
     *
     * @param uid
     * @return 返回token
     */
    String login(Long uid);

    /**
     * return uid if token valid
     *
     * @param token
     * @return
     */
    Long getValidUid(String token);
}
