package com.hbnu.service;

import com.hbnu.dao.DBOperator;
import com.hbnu.pojo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 陈迪凯
 * @date 2021-11-20 21:06
 */
public class ServerThread implements Runnable {
    private Socket socket;

    // 存储登录成功的用户信息，ConcurrentHashMap是一个线程安全的集合
    private final static ConcurrentHashMap<Socket, String> map = new ConcurrentHashMap<>();

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
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
     * 服务端主页功能
     *
     * @param socket socket对象
     */
    private void systemFunction(Socket socket) {

    }

    /**
     * 服务端首页功能
     *
     * @param socket socket对象
     */
    private void homeFunction(Socket socket) throws IOException {
        // 获取流对象
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        String s = null;  // 接收客户端传过来的功能选项（数字字符串）

        tab:
        while (true) {
            try {
                s = (String) objectInputStream.readObject();  // 接收客户端发送过来的功能数字字符串
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (s != null) {
                switch (s) {
                    case "1":
                        try {
                            // 服务端验证用户注册是否成功
                            User user = (User) objectInputStream.readObject();  // 接收客户端发送过来的用户注册信息

                            // 将用户注册信息添加到数据表中
                            DBOperator dbOperator = new DBOperator();
                            int count = dbOperator.addUser(user);

                            // 将注册结果返回给客户端
                            String registerMessage = (count == 1 ? "注册成功" : "注册失败，账号已存在");
                            objectOutputStream.writeObject(registerMessage);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "2":
                        // TODO 服务端登录功能模块
                        try {
                            // 获取客户端登录的用户信息
                            User loginUser = (User) objectInputStream.readObject();

                            // 判断用户是否登录过本系统
                            if (map.values().contains(loginUser.getUsername())) {  // 用户已登录过本系统
                                objectOutputStream.writeObject(true);
                                break;
                            } else {  // 用户未登录过本系统
                                objectOutputStream.writeObject(false);

                                // 去数据库中校验用户登录信息
                                DBOperator dbOperator = new DBOperator();
                                boolean flag = dbOperator.findUser(loginUser);  // flag表示用户信息正确还是错误

                                // 将登录成功或登录失败的结果返回给客户端
                                objectOutputStream.writeObject(flag);

                                if (flag) {
                                    System.out.println("用户【" + loginUser.getUsername() + "】上线");
                                    break tab;
                                }

                            }

                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }
}
