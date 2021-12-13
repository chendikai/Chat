package com.hbnu.controller;

import com.hbnu.service.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端启动类
 *
 * @author 陈迪凯
 * @date 2021-11-20 21:08
 */
public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);

            System.out.println("聊天室服务端已启动，等待客户端用户连接......");

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
