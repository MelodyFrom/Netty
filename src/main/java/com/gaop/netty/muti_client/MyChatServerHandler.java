package com.gaop.netty.muti_client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gaopeng@doctorwork.com
 * @date 2019-07-31 23:26
 **/
public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {

     /**
     * 用于保存每个建立好连接的对象
     */
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 多个客户端与服务器建立连接，客户端与服务器建立连接--XXX已上线；客户端与服务器端失去连接--XXX已下线 <br/>
     * 上线/下线的消息需要向所有建立连接的客户端进行广播 <br/>
     * 类似于聊天室，客户端 a 在发出一条消息后，其他在线的客户端都会收到消息。 <br/>
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush("收到[" + channel.remoteAddress() + "]发送的消息..." + msg + "\n");
            } else {
                ch.writeAndFlush("收到[自己]的消息" + msg + "\n");
            }
        });
    }

    /**
     * 连接建立的回调
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 广播其他客户端，新的客户端加入--广播,channelGroup 有简化操作的方法
        channelGroup.writeAndFlush("[服务器]-" + channel.remoteAddress() + "加入\n");
        // 先广播，再加入
        channelGroup.add(channel);
    }

    /**
     * 连接断掉的回调
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器]-" + channel.remoteAddress() + "下线\n");
        // 当连接断掉后，channelGroup 会自动地 remove 掉已经失效的连接
        //channelGroup.remove(channel);
        System.out.println(channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "下线...");
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
