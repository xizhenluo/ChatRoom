package com.lxz.chatroom.common.user.service;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/5
 */
public interface IpService {
    /**
     * async parse ip details
     * @param uid
     */
    void refreshIpDetailAsync(Long uid);
}
