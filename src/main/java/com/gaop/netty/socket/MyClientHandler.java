package com.gaop.netty.socket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gaopeng@doctorwork.com
 * @date 2019-07-31 8:34
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<String>{

    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        System.out.println("client output: " + msg);// 服务器端向客户端所发送的数据
        ctx.writeAndFlush("from client: " + System.currentTimeMillis());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自客户端的问候！");
    }
}
