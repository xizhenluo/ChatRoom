package com.lxz.chatroom.common.common.exception;

import cn.hutool.http.ContentType;
import com.google.common.base.Charsets;
import com.lxz.chatroom.common.common.domain.vo.resp.ApiResult;
import com.lxz.chatroom.common.common.utils.JsonUtils;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public enum HttpErrorEnum {
    ACCESS_DENIED(401, "please re-login as expired");

    private final Integer httpCode;
    private final String description;

    public void sendHttpError(HttpServletResponse response) throws IOException {
        response.setStatus(httpCode);
        response.setContentType(ContentType.JSON.toString(Charsets.UTF_8));
        response.getWriter().write(JsonUtils.toStr(ApiResult.fail(httpCode, description)));
    }
}
