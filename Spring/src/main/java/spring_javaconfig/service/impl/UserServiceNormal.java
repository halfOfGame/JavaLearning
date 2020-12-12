package spring_javaconfig.service.impl;


import spring_javaconfig.dao.UserDao;
import spring_javaconfig.service.UserService;

public class UserServiceNormal implements UserService {

    private UserDao userDao;


    public UserServiceNormal() {
        super();
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceNormal(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void add() {
        userDao.add();
    }
}
