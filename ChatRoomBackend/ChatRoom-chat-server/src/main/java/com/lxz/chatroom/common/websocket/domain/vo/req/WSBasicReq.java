package com.lxz.chatroom.common.websocket.domain.vo.req;

import lombok.Data;

/**
 * @description
 * @author LuoXizhen
 * @date 2025-09-21
 */
@Data
public class WSBasicReq {
    /**
     * @see com.lxz.chatroom.common.websocket.domain.enums.WSReqTypeEnum
     */
    private Integer type;
    private String data;
}
