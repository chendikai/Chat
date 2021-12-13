package com.hbnu.controller;

import com.hbnu.pojo.User;
import com.hbnu.view.Page;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 聊天系统客户端
 *
 * @author 陈迪凯
 * @date 2021-11-20 21:00
 */
public class Client {
    public static void main(String[] args) {
        try {
            // 获取socket对象
            Socket socket = new Socket("127.0.0.1", 9999);

            while (true) {
                // 首页功能
                homeFunction(socket);

                // 主页功能
                systemFunction(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 系统主页功能
     *
     * @param socket 客户端socket对象
     */
    private static void systemFunction(Socket socket) {
        System.out.println("=======主页功能功能=====");
    }

    /**
     * 系统首页功能
     *
     * @param socket 客户端socket对象
     */
    private static void homeFunction(Socket socket) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // 获取流对象
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        String s1;  // 接收用户输入的功能选项
        int count = 0;  // 客户端记录用户输入的登录次数
        tab:
        while (true) {
            Page.menu();  // 首页界面
            s1 = scanner.next();  // 获取用户输入的功能
            objectOutputStream.writeObject(s1);  // 客户端发送功能数字给服务端
            switch (s1) {
                case "1":
                    // TODO 注册功能模块
                    // 1、获取用户注册信息
                    User registerUser = Page.registerPage();

                    // 2、将注册的用户信息对象发送给服务器
                    objectOutputStream.writeObject(registerUser);

                    try {
                        // 3、接收服务端返回的注册结果信息
                        String message = (String) objectInputStream.readObject();
                        System.out.println(message);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    // TODO 登录功能模块
                    boolean loginFlag = false;   // 登录成功或登录失败的标记

                    // 客户端显示登录界面
                    User loginUser = Page.login();  // 获取用户输入的登录信息

                    // 将用户的登录信息发送给服务端
                    objectOutputStream.writeObject(loginUser);

                    // 验证用户是否登录过本系统
                    try {
                        boolean flag = (boolean) objectInputStream.readObject();

                        if (flag) {
                            System.out.println("用户" + loginUser.getUsername() + "已登录过本系统");
                            break;
                        } else {
                            // 接收服务端返回的登录结果
                            loginFlag = (boolean) objectInputStream.readObject();

                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    if (loginFlag) {  // 登录成功
                        System.out.println("登录成功");
                        break tab;
                    } else {
                        count++;
                        if (count == 3) {
                            System.out.println("请不要暴力登录");
                            System.exit(1);
                        } else {
                            System.out.println("用户名或密码错误，请重新登录");
                            break;
                        }
                    }

            }
        }
    }
}
