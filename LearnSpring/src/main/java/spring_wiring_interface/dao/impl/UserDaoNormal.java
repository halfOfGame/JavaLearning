package spring_wiring_interface.dao.impl;

import org.springframework.stereotype.Repository;
import spring_wiring_interface.dao.UserDao;

@Repository
public class UserDaoNormal implements
        UserDao {
    @Override
    public void add() {
        System.out.println("添加用户到数据库中....");
    }
}
