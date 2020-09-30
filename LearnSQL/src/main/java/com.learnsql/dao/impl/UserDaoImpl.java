package com.learnsql.dao.impl;

import com.learnsql.DBUtils.DBUtils;
import com.learnsql.dao.UserDao;
import com.learnsql.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User findUserById(int id) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        User user = null;
        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM t_user WHERE id = " + id);
            if (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Date birthday = resultSet.getDate(3);
                user = new User(userId, name, birthday);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeALl(resultSet, statement, connection);
        }
        return user;
    }

    @Override
    public List<User> findAllUser() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> list = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM t_user");
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Date birthday = resultSet.getDate(3);
                User user = new User(userId, name, birthday);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeALl(resultSet, statement, connection);
        }
        return list;
    }

    @Override
    public int addUser(User user) {
        String sql = "INSERT INTO t_user VALUES(?,?,?)";
        java.util.Date java_Util_Date = user.getBirthday();
        java.sql.Date java_Sql_Date = new java.sql.Date(java_Util_Date.getTime());
        int flagNumber = DBUtils.executeUpdate(sql, user.getId(), user.getName(), java_Sql_Date);
        return flagNumber;
    }

    @Override
    public int deleteUser(int id) {
        String sql = "DELETE FROM t_user WHERE id = ?";
        int flagNumber = DBUtils.executeUpdate(sql, id);
        return flagNumber;
    }
}
