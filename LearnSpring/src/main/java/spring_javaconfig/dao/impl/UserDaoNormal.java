package spring_javaconfig.dao.impl;


import spring_javaconfig.dao.UserDao;

public class UserDaoNormal implements UserDao {

    @Override
    public void add() {
        System.out.println("添加用户到数据库....");
    }
}
