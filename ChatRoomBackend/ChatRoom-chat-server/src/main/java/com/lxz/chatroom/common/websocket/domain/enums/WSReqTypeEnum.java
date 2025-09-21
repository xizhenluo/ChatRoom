package com.lxz.chatroom.common.websocket.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description websocket fronted request type enums
 * @author LuoXizhen
 * @date 2025-09-21
 */
@AllArgsConstructor
@Getter
public enum WSReqTypeEnum {
    LOGIN(1, "request for login QR"),
    HEARTBEAT(2, "heartbeat package"),
    AUTHORIZE(3, "login authorization")
    ;

    private final Integer type;
    private final String description;

    private static Map<Integer, WSReqTypeEnum> cache;

    static {
        cache = Arrays.stream(WSReqTypeEnum.values()).collect(Collectors.toMap(WSReqTypeEnum::getType, Function.identity()));
    }

    public static WSReqTypeEnum of(Integer type) {
        return cache.get(type);
    }
}
