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
public class WSLoginSuccess {
    private Long uid;
    private String avatar;
    private String token;
    private String name;
    //用户权限 0普通用户 1超管
    private Integer power;
}
