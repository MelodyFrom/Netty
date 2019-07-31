package com.gaop.netty.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * 继承的 handler 泛型，实际是指服务请求体的类型限制。socket 编程的内容处理器 handler
 * @author gaopeng@doctorwork.com
 * @date 2019-07-30 23:14
 **/
public class MyServerHandler extends SimpleChannelInboundHandler<String>{

    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + ", " + msg);
        ctx.channel().writeAndFlush("from server:" + UUID.randomUUID());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         cause.printStackTrace();
        ctx.close();
    }
}
