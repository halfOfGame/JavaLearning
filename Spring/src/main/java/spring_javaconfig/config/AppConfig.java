package spring_javaconfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring_javaconfig.dao.UserDao;
import spring_javaconfig.dao.impl.UserDaoNormal;
import spring_javaconfig.service.UserService;
import spring_javaconfig.service.impl.UserServiceNormal;

@Configuration
public class AppConfig {

    @Bean
    public UserDao userDaoNormal() {
        System.out.println("创建UserDao对象");
        return new UserDaoNormal();
    }

    @Bean
    public UserService userServiceNormal(UserDao userDao) {
        System.out.println("创建UserService对象");
        UserServiceNormal userService = new UserServiceNormal();
        userService.setUserDao(userDao);
        return userService;
    }
}
