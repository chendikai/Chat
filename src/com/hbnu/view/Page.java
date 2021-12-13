package com.hbnu.view;

import com.hbnu.pojo.User;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 聊天系统页面
 *
 * @author 陈迪凯
 * @date 2021-11-20 20:41
 */
public class Page {

    /**
     * 系统首页
     */
    public static void  menu() {
        System.out.println("请选择功能（输入数字）：");
        System.out.println("1.注册\t2.登录\t3.找回密码\t4.退出");
    }

    /**
     * 用户注册页面
     *
     * @return 用户注册信息
     */
    public static User registerPage() {
        // 用于封装用户输入的数据
        User user = new User();

        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入用户名：");
        String username = scanner.nextLine();  // 获取用户输入的用户名

        String password;
        int pCount = 0; // 记录用户输入密码的次数
        do {
            pCount++;
            if (pCount == 4) {
                System.out.println("密码格式错误次数过多");
                System.exit(1);
            }
            if (pCount > 1) {
                System.out.println("密码格式不符合要求，请重新输入密码：");
                password = scanner.nextLine();
            } else {
                System.out.println("请输入密码：");
                password = scanner.nextLine();
            }
        } while (!Pattern.matches("^[A-Za-z0-9]+$", password));

        String email;
        int eCount = 0; // 记录用户输入邮箱的次数
        do {
            eCount++;
            if (eCount == 4) {
                System.out.println("邮箱格式错误次数过多");
                System.exit(1);
            }
            if (eCount > 1) {
                System.out.println("邮箱格式不符合要求，请重新输入邮箱：");
                email = scanner.nextLine();
            } else {
                System.out.println("请输入邮箱：");
                email = scanner.nextLine();
            }
        } while (!Pattern.matches("[a-zA-Z0-9]+@[A-Za-z0-9]+\\.[a-zA-Z0-9]+", email));

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        return user;
    }

    /**
     * 登录界面
     *
     * @return 用户输入的登录信息
     */
    public static User login() {
        User user = new User();

        Scanner scanner = new Scanner(System.in);

        System.out.println("请输入账号：");
        String username = scanner.nextLine();

        System.out.println("请输入密码：");
        String password = scanner.nextLine();

        user.setUsername(username);
        user.setPassword(password);

        return user;
    }

}
