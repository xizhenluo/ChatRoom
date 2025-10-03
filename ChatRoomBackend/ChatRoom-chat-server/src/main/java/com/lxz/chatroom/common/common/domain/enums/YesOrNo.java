package com.lxz.chatroom.common.common.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/3
 */
@AllArgsConstructor
@Getter
public enum YesOrNo {
    NO(0, "no"),
    YES(1, "yes"),
    ;

    private final Integer status;
    private final String description;
}
