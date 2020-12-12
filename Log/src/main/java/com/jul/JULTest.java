package com.jul;

import org.junit.Test;

import java.io.IOException;
import java.util.logging.*;

/**
 * @author halfOfGame
 * @create 2020-03-16,16:40
 */
public class JULTest {
    @Test
    public void testQuick() throws Exception {
        //1.获取日志记录器对象
        Logger logger = Logger.getLogger("com.jul.JULTest");
        //2.日志记录的输出
        logger.info("Hello JUL");

        //通用方法进行日志记录
        logger.log(Level.INFO, "info message");

        //通过占位符输出变量的值
        String name = "halfOfGame";
        int age = 21;
        logger.log(Level.INFO, "用户信息：{0},{1}", new Object[]{name, age});
    }
    
    @Test
    public void testLogLevel() {
        Logger logger = Logger.getLogger("com.jul.JULTest");

        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        //下面三个默认不会执行
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    @Test
    public void testLogConfig() throws IOException {
        Logger logger = Logger.getLogger("com.jul.JULTest");

        logger.setUseParentHandlers(false);

        ConsoleHandler consoleHandler = new ConsoleHandler();
        SimpleFormatter simpleFormatter = new SimpleFormatter();

        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);

        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);

        FileHandler fileHandler = new FileHandler("logs/jul.log");
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(fileHandler);

        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        //下面三个默认不会执行
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
        logger.config("config");
    }

    @Test
    public void testLogParent(){
        
    }
}
