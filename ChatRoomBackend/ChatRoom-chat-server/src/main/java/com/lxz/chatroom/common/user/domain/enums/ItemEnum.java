package com.lxz.chatroom.common.user.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description items enum
 * @date 2025/10/3
 */
@AllArgsConstructor
@Getter
public enum ItemEnum {
    MODIFY_NAME_CARD(1L, ItemTypeEnum.MODIFY_NAME_CARD, "modify-name card"),
    LIKE_BADGE(2L, ItemTypeEnum.BADGE, "hot-liked badge"),
    REG_TOP10_BADGE(3L, ItemTypeEnum.BADGE, "first-10 badge"),
    REG_TOP100_BADGE(4L, ItemTypeEnum.BADGE, "first-100 badge"),
    PLANET(5L, ItemTypeEnum.BADGE, "planet"),
    CONTRIBUTOR(6L, ItemTypeEnum.BADGE, "contributor"),
    ;

    private final Long id;
    private final ItemTypeEnum typeEnum;
    private final String desc;

    private static Map<Long, ItemEnum> cache;

    static {
        cache = Arrays.stream(ItemEnum.values()).collect(Collectors.toMap(ItemEnum::getId, Function.identity()));
    }

    public static ItemEnum of(Long type) {
        return cache.get(type);
    }
}
