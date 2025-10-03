package com.lxz.chatroom.common.common.exception;

import com.lxz.chatroom.common.common.domain.vo.resp.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/3
 */
@RestControllerAdvice // to globally handle exceptions from restcontrollers
@Slf4j
public class GlobalExceptionHandler {
    /**
     * exceptions that should be displayed to frontend
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<?> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder errorMsg = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> errorMsg.append(fieldError.getField()).append(fieldError.getDefaultMessage()).append(","));
        return ApiResult.fail(CommonErrorEnum.PARAM_INVALID.getErrorCode(), errorMsg.substring(0, errorMsg.length() - 1));
    }

    /**
     * business exception
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ApiResult<?> businessException(BusinessException e) {
        log.info("Business exception caused by: {}", e.getErrorMsg());
        return ApiResult.fail(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * internal errors inside backend (i.e. database error) that should not be displayed to frontend
     * also the ultimate guardline for all exceptions and errors, so should print e
     * @param e
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public ApiResult<?> throwable(Throwable e) {
        log.error("System exception caused by reason: {}", e.getMessage(), e);
        return ApiResult.fail(CommonErrorEnum.SYSTEM_ERROR);
    }
}
