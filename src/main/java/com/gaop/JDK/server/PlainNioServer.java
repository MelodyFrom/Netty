package com.gaop.JDK.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 基于 JDK 的非阻塞 IO 服务端
 * @author gaopeng@doctorwork.com
 * @date 2019-07-27 22:25
 **/
public class PlainNioServer {

    public void server(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocket.bind(address); // 将服务器绑定到指定端口
        Selector selector = Selector.open(); // 打开 select 来处理 channel
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 将 ServerSocket 注册到 selector 以接受连接
        final ByteBuffer msg = ByteBuffer.wrap("Hi!".getBytes());
        for (;;) {
            try {
                selector.select(); // 等待需要处理的新事件，阻塞将一直持续到下一个传入事件
            } catch (IOException ex) {
                ex.printStackTrace();
                break;
            }
        }
        Set<SelectionKey> readyKeys = selector.selectedKeys(); // 获取所有接收事件的 SelectionKey 实例
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            try {
                if (key.isAcceptable()) { // 检查事件是否是一个新的已经就绪可以被接受的连接
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    // 接受客户端，并将它注册到选择器
                    client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                    System.out.println("Accepted connection from " +client);
                }
                if (key.isWritable()) { // 检查套接字知否已经准备好写数据
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    while (byteBuffer.hasRemaining()) {
                        if (client.write(byteBuffer) == 0) { // 将数据写到已连接的客户端
                            break;
                        }
                    }
                    client.close(); // 关闭连接
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                key.cancel();
                try {
                    key.channel().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
