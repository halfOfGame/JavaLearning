package com.learnsql.test;

import com.learnsql.dao.UserDao;
import com.learnsql.dao.impl.UserDaoImpl;
import com.learnsql.entity.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Test {
    private static UserDao userDao = new UserDaoImpl();
    //查找用户
    public static void findAUser(int id) {
        User user = userDao.findUserById(id);
        if (user == null)
            System.out.println("查无此人！！");
        else
            System.out.println(user);
    }

    //查找所有用户
    public static void findALlUser() {
        List<User> list = userDao.findAllUser();
        for (User user : list)
            System.out.println(user);
    }

    //增加用户
    public static void addUser(int id, String name, String birthday) {
        Date date = null;
        try {
            date= new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User user = new User(id, name, date);
        int flagNumber = userDao.addUser(user);
        if (flagNumber > 0)
            System.out.println("添加成功！！");
        else
            System.out.println("添加失败！！");
    }

    public static void deleteUser(int id) {
        int n = userDao.deleteUser(id);
        if (n > 0)
            System.out.println("删除成功");
        else
            System.out.println("删除失败");
    }

    public static void main(String[] args) {

        addUser(4,"test", "2035-8-14");
        findALlUser();
    }
}
