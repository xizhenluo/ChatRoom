package com.lxz.chatroom.common.websocket.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.lxz.chatroom.common.user.dao.UserDao;
import com.lxz.chatroom.common.user.domain.entity.User;
import com.lxz.chatroom.common.user.service.LoginService;
import com.lxz.chatroom.common.websocket.domain.dto.WSChannelExtraDTO;
import com.lxz.chatroom.common.websocket.domain.enums.WSRespTypeEnum;
import com.lxz.chatroom.common.websocket.domain.vo.resp.WSBasicResp;
import com.lxz.chatroom.common.websocket.domain.vo.resp.WSLoginUrl;
import com.lxz.chatroom.common.websocket.service.WebSocketService;
import com.lxz.chatroom.common.websocket.service.adapter.WebSocketAdapter;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description manage websocket logic
 * @date 2025/9/22
 */
@Service
public class WebSocketServiceImpl implements WebSocketService {
    /**
     * keep and manage all the connections of users (including loged-in-users and visitors)
     */
    private static final ConcurrentHashMap<Channel, WSChannelExtraDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();

    public static final Duration DURATION = Duration.ofHours(1);
    public static final int MAXIMUM_SIZE = 10000;
    /**
     * keep mapping relations between the login code and channel temporarily
     */
    private static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .maximumSize(MAXIMUM_SIZE)
            .expireAfterWrite(DURATION)
            .build();

    @Autowired
    @Lazy
    private WxMpService wxMpService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserDao userDao;

    @Override
    public void connect(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WSChannelExtraDTO());
    }

    @SneakyThrows
    @Override
    public void handleLoginRequest(Channel channel) {
        // step 1: generate random code
        Integer code = generateLoginCode(channel);
        // step 2: ask wechat for with-param QR code
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) DURATION.getSeconds());
        // step 3: push QR code to frontend
        sendMsg(channel, WebSocketAdapter.buildResp(wxMpQrCodeTicket));
    }

    @Override
    public void remove(Channel channel) {
        ONLINE_WS_MAP.remove(channel); // remove those connections closed
        // todo need to do when user offline (i.e. a broadcast)
    }

    @Override
    public void scanLoginSuccess(Integer code, Long uid) {
        // ensure that the connection exists
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(code);
        if (Objects.isNull(channel)) { return; }
        User user = userDao.getById(uid);
        // remove the code
        WAIT_LOGIN_MAP.invalidate(code);
        // get token by calling login module
        String token = loginService.login(uid);
        // respond to user
        afterLoginSuccess(channel, user, token);
    }

    private void afterLoginSuccess(Channel channel, User user, String token) {
        // save uid into WSChannelExtraDTO
        WSChannelExtraDTO wsChannelExtraDTO = ONLINE_WS_MAP.get(channel);
        wsChannelExtraDTO.setUid(user.getId());
        // todo events related to user online
        // respond to user
        sendMsg(channel, WebSocketAdapter.buildResp(user, token));
    }

    @Override
    public void waitAuthorization(Integer code) {
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(code);
        if (Objects.isNull(channel)) { return; }
        sendMsg(channel, WebSocketAdapter.buildWaitAuthorizationResp());
    }

    @Override
    public void authorize(Channel channel, String token) {
        Long uid = loginService.getValidUid(token);
        if (Objects.isNull(uid)) {
            sendMsg(channel, WebSocketAdapter.buildInvalidTokenResp());
        } else {
            User user = userDao.getById(uid);
            afterLoginSuccess(channel, user, token);
        }
    }

    private void sendMsg(Channel channel, WSBasicResp<?> resp) {
        channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(resp)));
    }

    private Integer generateLoginCode(Channel channel) {
        Integer code;
        do {
            code = RandomUtil.randomInt(Integer.MAX_VALUE);
        } while (Objects.nonNull(WAIT_LOGIN_MAP.asMap().putIfAbsent(code, channel)));
        return code;
    }
}
