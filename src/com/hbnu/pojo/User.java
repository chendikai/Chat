package com.hbnu.pojo;

import java.io.Serializable;

/**
 * 用户实体类
 *
 * @author 陈迪凯
 * @date 2021-11-20 20:45
 */
public class User implements Serializable {
    private static final long serialVersionUID = 2558014036614376806L;

    private String username;  // 用户名
    private String password;  // 密码
    private String email;     // 邮箱

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
