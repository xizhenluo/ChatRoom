package com.lxz.chatroom.common.user.service.adapter;

import com.lxz.chatroom.common.common.domain.enums.YesOrNo;
import com.lxz.chatroom.common.user.domain.entity.ItemConfig;
import com.lxz.chatroom.common.user.domain.entity.User;
import com.lxz.chatroom.common.user.domain.entity.UserBackpack;
import com.lxz.chatroom.common.user.domain.vo.resp.BadgeResp;
import com.lxz.chatroom.common.user.domain.vo.resp.UserInfoResp;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.springframework.beans.BeanUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/9/29
 */
public class UserAdapter {
    public static User buildUserSave(String openId) {
        return User.builder().openId(openId).build();
    }

    public static User buildAuthorizedUser(Long uid, WxOAuth2UserInfo userInfo) {
        User user = new User();
        user.setId(uid);
        user.setName(userInfo.getNickname());
        user.setAvatar(userInfo.getHeadImgUrl());
        user.setSex(userInfo.getSex());
        return user;
    }

    public static UserInfoResp buildUserInfo(User user, Integer modifyNameChance) {
        UserInfoResp vo = new UserInfoResp();
        BeanUtils.copyProperties(user, vo);
        vo.setModifyNameChance(modifyNameChance);
        return vo;
    }

    public static List<BadgeResp> buildBadgeResp(List<ItemConfig> badges, List<UserBackpack> backpacks, User user) {
        Set<Long> ownedItemId = backpacks.stream().map(UserBackpack::getItemId).collect(Collectors.toSet());
        return badges.stream().map(a -> {
            BadgeResp resp = new BadgeResp();
            BeanUtils.copyProperties(a, resp);
            resp.setOwning(ownedItemId.contains(a.getId()) ? YesOrNo.YES.getStatus() : YesOrNo.NO.getStatus());
            resp.setEquipped(Objects.equals(a.getId(), user.getItemId()) ? YesOrNo.YES.getStatus() : YesOrNo.NO.getStatus());
            return resp;
        }).sorted(Comparator.comparing(BadgeResp::getEquipped, Comparator.reverseOrder()) //
                .thenComparing(BadgeResp::getOwning, Comparator.reverseOrder())).collect(Collectors.toList());
    }
}
