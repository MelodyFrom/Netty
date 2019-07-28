package com.gaop.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author gaopeng@doctorwork.com
 * @description
 * @date 2019-07-28 15:45
 **/
public class TestServer {

    public static void main(String[] args) throws InterruptedException {
        // 基于 nio 的事件循环组
        EventLoopGroup bossGroup = new NioEventLoopGroup();// 接受连接，并将任务转给 client，由 client 处理
        EventLoopGroup clientGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, clientGroup).channel(NioServerSocketChannel.class).
                    childHandler(new TestServerInitialzer());

            ChannelFuture channelFuture = bootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            clientGroup.shutdownGracefully();
        }

    }
}
