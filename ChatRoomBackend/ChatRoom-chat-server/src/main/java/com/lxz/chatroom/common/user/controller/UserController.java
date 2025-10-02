package com.lxz.chatroom.common.user.controller;


import com.lxz.chatroom.common.common.domain.vo.resp.ApiResult;
import com.lxz.chatroom.common.common.interceptor.TokenInterceptor;
import com.lxz.chatroom.common.user.domain.vo.resp.UserInfoResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-09-22
 */
@RestController
@RequestMapping("/capi/user")
@Api(tags = "user-relevant api") // swagger note
public class UserController {
    @GetMapping("/userInfo")
    @ApiOperation(value = "get user's info") // swagger note
    public ApiResult<UserInfoResp> getUserInfo(HttpServletRequest request) {
        System.out.println(request.getAttribute(TokenInterceptor.UID));
        return null;
    }
}

