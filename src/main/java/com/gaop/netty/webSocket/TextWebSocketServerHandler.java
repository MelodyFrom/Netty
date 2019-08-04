package com.gaop.netty.webSocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDate;

/**
 * 用于 webSocket 文本信息的传输
 * @author gaopeng@doctorwork.com
 * @date 2019-08-03 15:34
 **/
public class TextWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

    /**
     * 专门用于处理 webSocket 中的文本诊
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到消息：" + msg.text());

        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间：" + LocalDate.now()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added:" + ctx.channel().id().asLongText()); // 全局唯一的id
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler removed:" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生");
        ctx.close();
    }
}
