package com.learnsql.DBUtils;

import java.sql.*;

public class DBUtils {
    //获取连接
    public static Connection getConnection() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mysql_learning?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong";
        String username = "root";
        String password = "root";
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //提取增加、删除、修改的功能
    public static int executeUpdate(String sql, Object...objects) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int flagNumber = 0;
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i + 1, objects[i]);
            }
            flagNumber = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeALl(null, preparedStatement, connection);
        }
        return flagNumber;
    }

    //关闭资源
    public static void closeALl(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
