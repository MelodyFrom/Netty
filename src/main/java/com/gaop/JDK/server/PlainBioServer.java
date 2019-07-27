package com.gaop.JDK.server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于 JDK 的阻塞网络编程
 * @author gaopeng@doctorwork.com
 * @date 2019-07-27 22:14
 **/
public class PlainBioServer {

    private static final int port = 8080;

    public static void main(String[] args) throws IOException {
        PlainBioServer server = new PlainBioServer();
        server.server(port);
    }

    public void server(int port) throws IOException {
        // 绑定端口号
        final ServerSocket socket = new ServerSocket(port);
        System.out.println("bio server start...");
        for (;;) {
            // 接受连接
            final Socket clientSocket = socket.accept();
            System.out.println("accept connection from " + clientSocket);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        OutputStream out = clientSocket.getOutputStream();
                        out.write("Hi!\r\n".getBytes());
                        out.flush();
                        clientSocket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start(); // 启动
        }
    }
}
