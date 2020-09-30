package com.funtl.hello.spring;


import com.funtl.hello.spring.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author halfOfGame
 * @create 2020-04-10,18:44
 */

public class MyTest {
    public static void main(String[] args) {
        // 获取 Spring 容器,位置在spring-context.xml
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");

        // 从 Spring 容器中获取对象
        UserService userService = (UserService) applicationContext.getBean("userService");
        userService.sayHi();
    }
}