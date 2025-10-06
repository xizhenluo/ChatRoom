package com.lxz.chatroom.common.user.controller;


import com.lxz.chatroom.common.common.domain.dto.RequestInfo;
import com.lxz.chatroom.common.common.domain.vo.resp.ApiResult;
import com.lxz.chatroom.common.common.utils.AssertUtil;
import com.lxz.chatroom.common.common.utils.RequestHolder;
import com.lxz.chatroom.common.user.domain.enums.RoleEnum;
import com.lxz.chatroom.common.user.domain.vo.req.BlackReq;
import com.lxz.chatroom.common.user.domain.vo.req.EquipBadgeReq;
import com.lxz.chatroom.common.user.domain.vo.req.ModifyNameReq;
import com.lxz.chatroom.common.user.domain.vo.resp.BadgeResp;
import com.lxz.chatroom.common.user.domain.vo.resp.UserInfoResp;
import com.lxz.chatroom.common.user.service.RoleService;
import com.lxz.chatroom.common.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "get user's info") // swagger note
    public ApiResult<UserInfoResp> getUserInfo() {
        return ApiResult.success(userService.getUserInfo(RequestHolder.get().getUid()));
    }

    @PutMapping("/modifyName")
    @ApiOperation(value = "modify user's name")
    public ApiResult<Void> modifyName(@Valid @RequestBody ModifyNameReq modifyNameReq) {
        userService.modifyName(RequestHolder.get().getUid(), modifyNameReq.getName());
        return ApiResult.success();
    }

    @GetMapping("/badges")
    @ApiOperation(value = "get list of availabe badges")
    public ApiResult<List<BadgeResp>> getBadges() {
        return ApiResult.success(userService.getBadges(RequestHolder.get().getUid()));
    }

    @PutMapping("/equipBadge")
    @ApiOperation(value = "equip a badge")
    public ApiResult<Void> equipBadge(@Valid @RequestBody EquipBadgeReq equipBadgeReq) {
        userService.equipBadge(RequestHolder.get().getUid(), equipBadgeReq.getItemId());
        return ApiResult.success();
    }

    @PutMapping("/black")
    @ApiOperation(value = "black a user")
    public ApiResult<Void> black(@Valid @RequestBody BlackReq blackReq) {
        Long uid = RequestHolder.get().getUid();
        boolean power = roleService.hasRower(uid, RoleEnum.ADMIN);
        AssertUtil.isTrue(power, "you have no power to black a user");
        userService.black(blackReq);
        return ApiResult.success();
    }
}

