package com.lxz.chatroom.common.user.service;

import com.lxz.chatroom.common.user.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lxz.chatroom.common.user.domain.vo.resp.BadgeResp;
import com.lxz.chatroom.common.user.domain.vo.resp.UserInfoResp;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author <a href="https://github.com/xizhenluo">xizhenluo</a>
 * @since 2025-09-22
 */
public interface UserService {
    Long register(User insertUser);

    UserInfoResp getUserInfo(Long uid);

    void modifyName(Long uid, String name);

    List<BadgeResp> getBadges(Long uid);
}
