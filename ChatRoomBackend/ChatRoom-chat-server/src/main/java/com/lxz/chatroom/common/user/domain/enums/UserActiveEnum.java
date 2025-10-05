package com.lxz.chatroom.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/5
 */
@AllArgsConstructor
@Getter
public enum UserActiveEnum {
    ONLINE(1, "online"),
    OFFLINE(2, "offline");

    private final Integer status;
    private final String description;
}
