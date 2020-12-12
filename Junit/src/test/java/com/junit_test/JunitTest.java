package com.junit_test;

import org.junit.jupiter.api.*;

/**
 * @author halfOfGame
 * @create 2020-03-16,14:42
 */
public class JunitTest {

    @BeforeAll
    //该注解只初始化一次，并且只能修饰静态方法
    static void beforeAll() {
        System.out.println("beforeAll");
    }

    @AfterAll
    //该注解只初始化一次，并且只能修饰静态方法
    static void afterAll() {
        System.out.println("afterAll");
    }

    @BeforeEach
    //该注解会在测试之前运行
    void beforeEach() {
        System.out.println("beforeEach");
    }

    @AfterEach
    //该注解会在测试之后运行
    void afterEach() {
        System.out.println("afterEach");
    }

    @Test
    void testJunit() {
        int res = 1 + 1;
        //判断res是否等于2，如果不等于则抛出异常
        Assertions.assertEquals(2, res);
        System.out.println("testJunit");
    }

    @Test
    void testJunit2() {
        System.out.println("testJunit2");
    }
}
