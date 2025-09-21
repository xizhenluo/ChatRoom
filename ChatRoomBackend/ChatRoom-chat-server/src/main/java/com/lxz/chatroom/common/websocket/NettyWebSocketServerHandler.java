package com.lxz.chatroom.common.websocket;

import cn.hutool.json.JSONUtil;
import com.lxz.chatroom.common.websocket.domain.enums.WSReqTypeEnum;
import com.lxz.chatroom.common.websocket.domain.vo.req.WSBasicReq;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @description
 * @author LuoXizhen
 * @date 2025-09-21
 */
@ChannelHandler.Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof WebSocketServerProtocolHandler.HandshakeComplete){
            System.out.println("handshake complete");
        } else if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("read idle state");
                // ws connection will automatically close, so here to do: user offline
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String message = msg.text();
        WSBasicReq wsBasicReq = JSONUtil.toBean(message, WSBasicReq.class);
        switch (WSReqTypeEnum.of(wsBasicReq.getType())) {
            case LOGIN:
                System.out.println("QR");
                ctx.channel().writeAndFlush(new TextWebSocketFrame("123")); // return data to frontend
                break;
            case HEARTBEAT:
                System.out.println("HEARTBEAT");
                break;
            case AUTHORIZE:
                System.out.println("AUTHORIZE");
        }

    }
}
