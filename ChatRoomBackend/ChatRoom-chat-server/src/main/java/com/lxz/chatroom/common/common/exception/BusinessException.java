package com.lxz.chatroom.common.common.exception;

import lombok.Data;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/3
 */
@Data
public class BusinessException extends RuntimeException {
    protected Integer errorCode;
    protected String errorMsg;

    public BusinessException(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.errorCode = CommonErrorEnum.BUSINESS_ERROR.getErrorCode();
        this.errorMsg = errorMsg;
    }
}
