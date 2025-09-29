package com.lxz.chatroom.common.websocket.service;

import io.netty.channel.Channel;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/9/22
 */
public interface WebSocketService {
    void connect(Channel channel);
    void handleLoginRequest(Channel channel);

    void remove(Channel channel);
    void scanLoginSuccess(Integer code, Long uid);
}
