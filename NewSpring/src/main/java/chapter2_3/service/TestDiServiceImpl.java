package chapter2_3.service;

import chapter2_3.dao.TestDiDao;

public class TestDiServiceImpl implements TestDiService {

    private TestDiDao testDiDao;

    public TestDiServiceImpl(TestDiDao testDiDao) {
        super();
        this.testDiDao = testDiDao;
    }

    @Override
    public void sayHello() {
        testDiDao.sayHello();
        System.out.println("TestDIService构造方法注入say:Hello, Study hard!");
    }
}
