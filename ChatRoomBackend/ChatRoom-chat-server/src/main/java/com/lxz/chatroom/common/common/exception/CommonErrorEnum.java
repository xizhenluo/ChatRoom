package com.lxz.chatroom.common.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/3
 */
@AllArgsConstructor
@Getter
public enum CommonErrorEnum implements ErrorEnum {
    SYSTEM_ERROR(-1, "something wrong with the system"),
    PARAM_INVALID(-2, "parameter validation failed"),
    BUSINESS_ERROR(0, "{0}"), // if no 0, MessageFormat will error
    LOCK_LIMIT(-3, "too frequency request");

    private final Integer errorCode;
    private final String errorMsg;

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}
