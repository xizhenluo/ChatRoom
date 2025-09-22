package com.lxz.chatroom.common.websocket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description to record mapping info of connections with frontend
 * @date 2025/9/22
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSChannelExtraDTO {
    /**
     * 前端如果登录了，记录uid
     */
    private Long uid;
}
