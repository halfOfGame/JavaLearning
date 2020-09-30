package spring_wiring_interface.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring_wiring_interface.dao.UserDao;
import spring_wiring_interface.service.UserService;

@Service
public class UserServiceNormal implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void add() {
        System.out.println("添加用户");
    }
}
