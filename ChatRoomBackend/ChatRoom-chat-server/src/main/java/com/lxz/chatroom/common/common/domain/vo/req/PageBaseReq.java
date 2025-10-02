package com.lxz.chatroom.common.common.domain.vo.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/2
 */
@Data
@ApiModel("basic turn-page request")
public class PageBaseReq {

    @ApiModelProperty("page size")
    @Min(0)
    @Max(50)
    private Integer pageSize = 10;

    @ApiModelProperty("page no. (from 1)")
    private Integer pageNo = 1;

    /**
     * obtain page in mybatisPlus
     *
     * @return
     */
    public Page plusPage() {
        return new Page(pageNo, pageSize);
    }
}
