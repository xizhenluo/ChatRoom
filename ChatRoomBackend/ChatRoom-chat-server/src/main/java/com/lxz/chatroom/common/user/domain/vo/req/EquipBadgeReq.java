package com.lxz.chatroom.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/5
 */
@Data
public class EquipBadgeReq {
    @ApiModelProperty("id of the item to be equipped")
    @NotNull
    private Long itemId;
}
