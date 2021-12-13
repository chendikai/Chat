package com.hbnu.dao;

import com.hbnu.pojo.User;
import com.hbnu.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库操作
 *
 * @author 陈迪凯
 * @date 2021-11-20 21:25
 */
public class DBOperator {

    private Connection connection = null;

    public DBOperator() {
        try {
            // 获取数据库连接
            this.connection = DBUtils.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 1912演示文档注释
     *
     * @param user 1912
     * @return 1912
     */
    public int addUser(User user) {
        PreparedStatement ps = null;
        int count = 0;

        try {
            // 开启事务
            connection.setAutoCommit(false);

            // 获取数据库操作对象，进行SQL预编译
            String sql = "insert into tb_user(username, password, email) values(?, ?, ?)";
            ps = connection.prepareStatement(sql);

            // 执行SQL语句
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            count = ps.executeUpdate();

            // 提交事务
            connection.commit();
        } catch (SQLException e) {
            try {
                // 回滚事务
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

        return count;
    }

    /**
     * 登录校验
     *
     * @param loginUser 用户输入的登录信息
     * @return 登录成功或失败的标记
     */
    public boolean findUser(User loginUser) {

        // 登录成功或登录失败的标记
        boolean flag = false;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection.setAutoCommit(false);  // 开启事务

            String sql = "select * from tb_user where username = ? and password = ?";
            ps = connection.prepareStatement(sql);

            ps.setString(1, loginUser.getUsername());
            ps.setString(2, loginUser.getPassword());
            rs = ps.executeQuery();

            while (rs.next()) {
                flag = true;
                break;
            }
            connection.commit();  // 提交事务
        } catch (SQLException e) {
            try {
                // 回滚事务
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            DBUtils.close(rs, ps, connection);
        }

        return flag;
    }
}
