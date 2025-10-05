package com.lxz.chatroom.common.user.domain.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/4
 */
@Data
public class BadgeResp {
    @ApiModelProperty("badge id")
    private Long id;
    @ApiModelProperty("badge image")
    private String img;
    @ApiModelProperty("badge description")
    private String description;
    @ApiModelProperty("own or not 0-no 1-yes")
    private Integer owning;
    @ApiModelProperty("whether is equipped now 0-no 1-yes")
    private Integer equipped;
}
