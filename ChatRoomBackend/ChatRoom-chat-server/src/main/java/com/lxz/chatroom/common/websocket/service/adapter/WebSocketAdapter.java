package com.lxz.chatroom.common.websocket.service.adapter;

import com.lxz.chatroom.common.user.domain.entity.User;
import com.lxz.chatroom.common.websocket.domain.enums.WSRespTypeEnum;
import com.lxz.chatroom.common.websocket.domain.vo.resp.WSBasicResp;
import com.lxz.chatroom.common.websocket.domain.vo.resp.WSLoginSuccess;
import com.lxz.chatroom.common.websocket.domain.vo.resp.WSLoginUrl;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/9/22
 */
public class WebSocketAdapter {
    public static WSBasicResp<?> buildResp(WxMpQrCodeTicket wxMpQrCodeTicket) {
        WSBasicResp<WSLoginUrl> resp = new WSBasicResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_URL.getType());
        resp.setData(new WSLoginUrl(wxMpQrCodeTicket.getUrl()));
        return resp;
    }

    public static WSBasicResp<?> buildResp(User user, String token) {
        WSBasicResp<WSLoginSuccess> resp = new WSBasicResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_SUCCESS.getType());
        WSLoginSuccess loginSuccess = WSLoginSuccess.builder()
                .uid(user.getId())
                .avatar(user.getAvatar())
                .name(user.getName())
                .token(token)
                .build();
        resp.setData(loginSuccess);
        return resp;
    }

    public static WSBasicResp<?> buildWaitAuthorizationResp() {
        WSBasicResp<Void> resp = new WSBasicResp<>();
        resp.setType(WSRespTypeEnum.LOGIN_SCAN_SUCCESS.getType());
        return resp;
    }

    public static WSBasicResp<?> buildInvalidTokenResp() {
        WSBasicResp<Void> resp = new WSBasicResp<>();
        resp.setType(WSRespTypeEnum.INVALID_TOKEN.getType());
        return resp;
    }
}
