package com.lxz.chatroom.common.user.domain.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/3
 */
@Data
public class ModifyNameReq {
    @ApiModelProperty("new username")
    @NotBlank
    @Length(max = 10, message = "name cannot be more than 10 characters")
    private String name;
}
