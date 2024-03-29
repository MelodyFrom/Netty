package com.gaop.netty.heart_beat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author gaopeng@doctorwork.com
 * @date 2019-08-03 8:36
 **/
public class MyServerInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        // 空闲状态监测处理器
        pipeline.addLast(new IdleStateHandler(5, 7, 10,
                TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
