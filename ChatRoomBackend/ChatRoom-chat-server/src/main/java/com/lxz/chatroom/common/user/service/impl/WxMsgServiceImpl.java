package com.lxz.chatroom.common.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lxz.chatroom.common.user.dao.UserDao;
import com.lxz.chatroom.common.user.domain.entity.User;
import com.lxz.chatroom.common.user.service.UserService;
import com.lxz.chatroom.common.user.service.WxMsgService;
import com.lxz.chatroom.common.user.service.adapter.TextBuilder;
import com.lxz.chatroom.common.user.service.adapter.UserAdapter;
import com.lxz.chatroom.common.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/9/29
 */
@Slf4j
@Service
public class WxMsgServiceImpl implements WxMsgService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    @Lazy
    private WxMpService wxMpService;

    @Autowired
    private WebSocketService webSocketService;

    @Value("${wx.mp.callback}")
    private String callback;

    private static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    /**
     * a hashmap to record the openid and the login-code
     */
    private static final ConcurrentHashMap<String, Integer> WAIT_AUTHORIZATION_MAP = new ConcurrentHashMap<>();

    @Override
    public WxMpXmlOutMessage scan(WxMpXmlMessage wxMpXmlMessage) {
        String openId = wxMpXmlMessage.getFromUser();
        Integer code = getEventKey(wxMpXmlMessage);
        if (Objects.isNull(code)) { return null; }
        User user = userDao.getByOpenId(openId);

        boolean hasRegistered = Objects.nonNull(user);
        boolean hasAuthorized = hasRegistered && StrUtil.isNotBlank(user.getAvatar());
        // if user exists and has avatar, he has registered
        if (hasAuthorized) {
            // then successful login logic: get channel via code
            webSocketService.scanLoginSuccess(code, user.getId());
            return TextBuilder.build("welcome", wxMpXmlMessage);
        }
        // if unregistered, then register
        if (!hasRegistered) {
            User insertUser = UserAdapter.buildUserSave(openId);
            userService.register(insertUser);
        }

        // push a link to let user authorize
        WAIT_AUTHORIZATION_MAP.put(openId, code);
        String authorizedURL = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callBack"));
        return TextBuilder.build("please click to login: <a href=\"" + authorizedURL + "\">login</a>", wxMpXmlMessage);
    }

    /**
     * user authorizes to use his info
     * @param userInfo
     */
    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {
        String openid = userInfo.getOpenid();
        User user = userDao.getByOpenId(openid);
        if (StrUtil.isBlank(user.getAvatar())) {
            fillUserInfo(user.getId(), userInfo);
        }
        // find code via openid, then find channel via the code
        Integer code = WAIT_AUTHORIZATION_MAP.remove(openid);
        webSocketService.scanLoginSuccess(code, user.getId());
    }

    private void fillUserInfo(Long uid, WxOAuth2UserInfo userInfo) {
        User user = UserAdapter.buildAuthorizedUser(uid, userInfo);
        // name should be unique, but wx nickname may conflict, so need to throw a DuplicateKetException and handle it
        // but here I did not do it
        userDao.updateById(user);
    }

    private Integer getEventKey(WxMpXmlMessage wxMpXmlMessage) {
        try {
            String eventKey = wxMpXmlMessage.getEventKey();
            String code = eventKey.replace("qrscene_", "");
            return Integer.parseInt(code);
        } catch (Exception e) {
            log.error("get event key error eventKey:{}", wxMpXmlMessage.getEventKey(),e);
            return null;
        }
    }
}
