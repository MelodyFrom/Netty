package com.gaop.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author gaopeng@doctorwork.com
 * @description
 * @date 2019-06-25 22:15
 **/
public class EchoServer {

    private final int port;

    public EchoServer (int port) {
        this.port = port;
    }



    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println(
                    "Usage:" + EchoServer.class.getSimpleName() + "<port>"
            );


        }
        int port = Integer.parseInt(args[0]);
        // 设置端口值并启动服务器
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        System.out.println("start...");
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // 创建 Event-LoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建 Server-Bootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 指定所使用的 NIO 传输 Channel
                    .channel(NioServerSocketChannel.class).
                    // 使用指定的端口设置套接字地址
                    localAddress(new InetSocketAddress(port))
                    // 添加一个 EchoServerHandler 到子 Channel 的 ChannelPipeline
                    .childHandler(new ChannelInitializer(){
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            // EchoServerHandler 被标注为 @Shareable，所以我们总是可以用同样的实例
                            channel.pipeline().addLast(serverHandler);
                        }
                    });
            // 异步绑定服务器；调用 sync() 方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            // 获取 Channel 的 CloseFuture，并阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            // 关闭 EventLoopGroup，释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}
