package com.lxz.chatroom.common.common.domain.vo.resp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lxz.chatroom.common.common.exception.ErrorEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/2
 */
@Data
@ApiModel("basic response body")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResult<T> {
    @ApiModelProperty("success flag: true or false")
    private Boolean success;
    @ApiModelProperty("error code")
    private Integer errCode;
    @ApiModelProperty("error message")
    private String errMsg;
    @ApiModelProperty("response object")
    private T data;

    public static <T> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<>();
        result.setData(null);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setData(data);
        result.setSuccess(Boolean.TRUE);
        return result;
    }

    public static <T> ApiResult<T> fail(Integer code, String msg) {
        ApiResult<T> result = new ApiResult<>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(code);
        result.setErrMsg(msg);
        return result;
    }

    public static <T> ApiResult<T> fail(ErrorEnum errorEnum) {
        ApiResult<T> result = new ApiResult<>();
        result.setSuccess(Boolean.FALSE);
        result.setErrCode(errorEnum.getErrorCode());
        result.setErrMsg(errorEnum.getErrorMsg());
        return result;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
