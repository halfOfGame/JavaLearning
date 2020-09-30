package com.learnsql.basetest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TransferAccount {
    public static void main(String[] args) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mysql_learning?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong";
        String name = "root";
        String password = "root";
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, name, password);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE t_account SET balance = Balance - 500 WHERE ID = 1");
            statement.executeUpdate("UPDATE t_account SET Balance = Balance + 500 WHERE ID = 2");
            connection.commit();
            System.out.println("转账成功！！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
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
}
