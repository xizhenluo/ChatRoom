package com.lxz.chatroom.common.websocket.domain.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @author LuoXizhen
 * @date 2025-09-21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSLoginUrl {
    private String loginUrl;
}
