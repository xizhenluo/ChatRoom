package com.lxz.chatroom.common.user.service.handler;

//import com.lxz.chatroom.common.user.service.WxMsgService;
import com.lxz.chatroom.common.user.service.adapter.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Map;

@Component
public class ScanHandler extends AbstractHandler {


//    @Autowired
//    private WxMsgService wxMsgService;

    @Value("${wx.mp.callback}")
    private String callback;

    private static final String URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map,
                                    WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        String code = wxMpXmlMessage.getEventKey();
        String openId = wxMpXmlMessage.getFromUser();
        // todo: deal with scaning event
        String authorizedURL = String.format(URL, wxMpService.getWxMpConfigStorage().getAppId(), URLEncoder.encode(callback + "/wx/portal/public/callBack"));
        return TextBuilder.build("please click to login: <a href=\"" + authorizedURL + "\">login</a>", wxMpXmlMessage);
//        return wxMsgService.scan(wxMpService, wxMpXmlMessage);


    }

}
