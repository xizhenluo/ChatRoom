package com.lxz.chatroom.common.common.utils;

import com.lxz.chatroom.common.common.domain.dto.RequestInfo;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description request context
 * @date 2025/10/3
 */
public class RequestHolder {
    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<>();

    public static void set(RequestInfo requestInfo) {
        threadLocal.set(requestInfo);
    }

    public static RequestInfo get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
