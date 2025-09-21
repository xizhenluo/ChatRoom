package com.lxz.chatroom.common.websocket.domain.vo.resp;

import lombok.Data;

/**
 * @description
 * @author LuoXizhen
 * @date 2025-09-21
 */
@Data
public class WSBasicResp<T> {
    /**
     * @see com.lxz.chatroom.common.websocket.domain.enums.WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
