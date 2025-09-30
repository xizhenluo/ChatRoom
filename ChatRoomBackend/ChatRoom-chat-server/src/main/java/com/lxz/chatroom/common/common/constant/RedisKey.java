package com.lxz.chatroom.common.common.constant;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/9/30
 */
public class RedisKey {
    private static final String BASE_KEY = "chatroom:chat";
    public static final String USER_TOKEN_STRING = "userToken:uid_%d";

    /**
     * generate a key for recording token
     * @param key
     * @param o
     * @return
     */
    public static String getKey(String key, Object... o) {
        return BASE_KEY + String.format(key, o);
    }
}
