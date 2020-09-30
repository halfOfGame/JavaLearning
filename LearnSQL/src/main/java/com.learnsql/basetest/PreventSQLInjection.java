package com.learnsql.basetest;

import java.sql.*;

public class PreventSQLInjection {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mysql_learning?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong";
        String name = "root";
        String password = "root";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, name, password);
            preparedStatement = connection.prepareStatement("SELECT Tname FROM table1 WHERE TKey = ? AND TName = ? AND TAge = ?");
            Integer Tkey = 2;
            String TName = "name1";
            Integer TAge = 21;
            preparedStatement.setString(1,Tkey.toString());
            preparedStatement.setString(2,TName);
            preparedStatement.setString(3,TAge.toString());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                System.out.println("找到数据");
            else
                System.out.println("未找到数据");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
