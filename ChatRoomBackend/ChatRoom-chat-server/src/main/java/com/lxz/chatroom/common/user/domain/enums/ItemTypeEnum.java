package com.lxz.chatroom.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description item type enum
 * @date 2025/10/3
 */
@AllArgsConstructor
@Getter
public enum ItemTypeEnum {
    MODIFY_NAME_CARD(1, "modify-name card"),
    BADGE(2, "badge"),
    ;

    private final Integer type;
    private final String desc;

    private static Map<Integer, ItemTypeEnum> cache;

    static {
        cache = Arrays.stream(ItemTypeEnum.values()).collect(Collectors.toMap(ItemTypeEnum::getType, Function.identity()));
    }

    public static ItemTypeEnum of(Integer type) {
        return cache.get(type);
    }
}