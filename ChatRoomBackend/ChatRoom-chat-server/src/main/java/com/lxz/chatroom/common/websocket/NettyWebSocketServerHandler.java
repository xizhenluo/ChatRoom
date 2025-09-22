package com.lxz.chatroom.common.websocket;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.lxz.chatroom.common.websocket.domain.enums.WSReqTypeEnum;
import com.lxz.chatroom.common.websocket.domain.vo.req.WSBasicReq;
import com.lxz.chatroom.common.websocket.service.WebSocketService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description
 * @author LuoXizhen
 * @date 2025-09-21
 */
@ChannelHandler.Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private WebSocketService webSocketService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        webSocketService = SpringUtil.getBean(WebSocketService.class);
        webSocketService.connect(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            System.out.println("handshake complete");
        } else if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("read idle state");
                // close ws connection
                ctx.channel().close();
                // todo: user offline
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String message = msg.text();
        WSBasicReq wsBasicReq = JSONUtil.toBean(message, WSBasicReq.class);
        switch (WSReqTypeEnum.of(wsBasicReq.getType())) {
            case LOGIN:
                webSocketService.handleLoginRequest(ctx.channel());
                break;
            case HEARTBEAT:
                System.out.println("HEARTBEAT");
                break;
            case AUTHORIZE:
                System.out.println("AUTHORIZE");
        }

    }
}
