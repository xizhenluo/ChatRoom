package com.lxz.chatroom.common.websocket.domain.enums;

import com.lxz.chatroom.common.websocket.domain.vo.resp.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description
 * @author LuoXizhen
 * @date 2025-09-21
 */
@AllArgsConstructor
@Getter
public enum WSRespTypeEnum {
    LOGIN_URL(1, "login QR response", WSLoginUrl.class),
    LOGIN_SCAN_SUCCESS(2, "user waiting for authorization with successful scan", null),
    LOGIN_SUCCESS(3, "return user info after successful login", WSLoginSuccess.class),
    MESSAGE(4, "new message", WSMessage.class),
    ONLINE_OFFLINE_NOTIFY(5, "online/offline notify", WSOnlineOfflineNotify.class),
    INVALID_TOKEN(6, "frontend needs re-login because token expired", null),
    BLACK(7, "someone is blacked", WSBlack.class),
    MARK(8, "mark a message", WSMsgMark.class),
    RECALL(9, "recall a message", WSMsgRecall.class),
    APPLY(10, "friend application", WSFriendApply.class),
    MEMBER_CHANGE(11, "member change", WSMemberChange.class)
    ;

    private final Integer type;
    private final String description;
    private final Class dataClass;

    private static Map<Integer, WSRespTypeEnum> cache;

    static {
        cache = Arrays.stream(WSRespTypeEnum.values()).collect(Collectors.toMap(WSRespTypeEnum::getType, Function.identity()));
    }

    public static WSRespTypeEnum of(Integer type) { return cache.get(type); }
}
