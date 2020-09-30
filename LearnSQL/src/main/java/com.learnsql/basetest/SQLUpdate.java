package com.learnsql.basetest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUpdate {
    public static void main(String[] args) throws SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mysql_learning?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong";
        String name = "root";
        String password = "root";
        Connection connection = null;
        Statement statement = null;

        try {
            //1.加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql_learning?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong", "root", "root");
            //3.创建会话
            statement= connection.createStatement();
            //4.发送sql
            int n = statement.executeUpdate("INSERT INTO table1 VALUES ( 7, \"name7\", 27 );");
            //5.处理结果
            if (n > 0) {
                System.out.println("插入数据成功");
            } else {
                System.out.println("插入数据失败");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            //6.关闭资源
            statement.close();
            connection.close();
        }



    }
}
