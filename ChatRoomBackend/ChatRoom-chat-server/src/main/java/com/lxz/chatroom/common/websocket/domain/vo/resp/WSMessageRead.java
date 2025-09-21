package com.lxz.chatroom.common.websocket.domain.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @author LuoXizhen
 * @date 2025-09-21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WSMessageRead {
    @ApiModelProperty("messages")
    private Long msgId;
    @ApiModelProperty("number of people having read")
    private Integer readCount;
}
