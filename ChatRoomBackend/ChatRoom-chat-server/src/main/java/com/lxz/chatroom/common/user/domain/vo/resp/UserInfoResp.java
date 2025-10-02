package com.lxz.chatroom.common.user.domain.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/2
 */
@Data
@ApiModel("user info")
public class UserInfoResp {
    @ApiModelProperty(value = "user's id")
    private Long id;
    @ApiModelProperty(value = "user's name")
    private String name;
    @ApiModelProperty(value = "user's avatar")
    private String avatar;
    @ApiModelProperty(value = "user's sex")
    private Integer sex;
    @ApiModelProperty(value = "user's rest chances to modify name")
    private Integer modifyNameChance;
}
