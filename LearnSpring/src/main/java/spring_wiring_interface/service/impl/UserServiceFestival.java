package spring_wiring_interface.service.impl;

import org.springframework.stereotype.Service;
import spring_wiring_interface.service.UserService;

@Service
public class UserServiceFestival implements UserService {



    @Override
    public void add() {
        System.out.println("注册用户并发送优惠券");
    }
}
