package com.learnsql.dao;

import com.learnsql.entity.User;

import java.util.List;

public interface UserDao {
    //员工管理系统

    //通过ID查找员工
    User findUserById(int id);

    //查找所有员工
    List<User> findAllUser();

    //增加一个员工
    int addUser(User user);

    //通过ID删除一个员工
    int deleteUser(int id);
}
