package com.lxz.chatroom.common.websocket;

import cn.hutool.core.net.url.UrlBuilder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * @author <a href="https://github.com/xizhenluo">LuoXizhen</a>
 * @Description
 * @date 2025/10/2
 */
public class MyHeaderCollectHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            UrlBuilder urlBuilder = UrlBuilder.ofHttp(request.uri());
            Optional<String> optionalToken = Optional.of(urlBuilder)
                    .map(UrlBuilder::getQuery)
                    .map(k -> k.get("token"))
                    .map(CharSequence::toString);
            // if token exists, add attribute token=${token} into websocket channel
            optionalToken.ifPresent(s -> NettyUtil.setAttr(ctx.channel(), NettyUtil.TOKEN, s));
            // remove the parameters from url
            request.setUri(urlBuilder.getPath().toString());

            // get IP info
            String ip = request.headers().get("X-Real-IP");
            if (StringUtils.isBlank(ip)) { // if no Nginx proxy, get real ip
                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
                ip = address.getAddress().getHostAddress();
            }
            // add attribute ip into websocket channel
            NettyUtil.setAttr(ctx.channel(), NettyUtil.IP, ip);

            // then the handler is useless
            ctx.pipeline().remove(this);
        }
        ctx.fireChannelRead(msg);
    }
}
