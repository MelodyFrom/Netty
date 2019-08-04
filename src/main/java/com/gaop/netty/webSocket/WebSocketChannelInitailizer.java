package com.gaop.netty.webSocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * @author gaopeng@doctorwork.com
 * @description
 * @date 2019-08-03 15:18
 **/
public class WebSocketChannelInitailizer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        // 聚合，在基于 netty 的http请求的使用很频繁。会将多个请求段聚合到一起
        pipeline.addLast(new HttpObjectAggregator(8192));
        //  netty 用户协助处理 webSocket 连接，入参表是 webSocket 的 uri 地址
        // 如此拼接的本地 webSocket 连接地址为 ws://localhost:9999/ws
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new TextWebSocketServerHandler());
    }
}
