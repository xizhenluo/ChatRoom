package com.lxz.chatroom.common.websocket.service;

import com.lxz.chatroom.common.websocket.domain.vo.resp.WSBasicResp;
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

    void waitAuthorization(Integer code);

    void authorize(Channel channel, String token);

    void broadcastMsg(WSBasicResp<?> resp);
}
