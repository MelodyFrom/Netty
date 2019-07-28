package com.gaop.netty.test;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * 自定义子处理器
 * @author gaopeng@doctorwork.com
 * @date 2019-07-28 15:48
 **/
public class TestServerInitialzer extends ChannelInitializer<SocketChannel>{

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline(); // 管道

        pipeline.addLast("httpServerCodec", new HttpServerCodec()); // 编解码
        pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler()); // 内容定义
    }
}
