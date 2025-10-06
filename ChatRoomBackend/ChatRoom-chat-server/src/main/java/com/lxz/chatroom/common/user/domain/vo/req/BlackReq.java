package com.lxz.chatroom.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/6
 */
@Data
public class BlackReq {
    @ApiModelProperty("id of black user")
    @NotNull
    private Long uid;
}
