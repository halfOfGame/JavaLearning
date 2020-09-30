package com.learnsql.basetest;

import com.learnsql.DBUtils.DBUtils;

import java.sql.*;

public class SQLQuery {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM table1");
            while (resultSet.next()) {
                int TKey = resultSet.getInt(1);
                String Tname = resultSet.getString(2);
                int TAge = resultSet.getInt(3);
                System.out.println(TKey + "\t" + Tname + "\t" + TAge);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeALl(resultSet, statement, connection);
        }
    }
}
