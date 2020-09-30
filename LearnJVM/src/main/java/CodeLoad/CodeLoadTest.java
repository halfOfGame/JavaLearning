package CodeLoad;

import java.sql.SQLOutput;

/**
 * @author halfOfGame
 * @create 2020-03-19,19:02
 */
class AAA{
    public AAA() {
        System.out.println("AAA的构造方法");
    }
    {
        System.out.println("AAA的构造块");
    }
    static {
        System.out.println("AAA的静态构造块");
    }
}

public class CodeLoadTest {
    {
        System.out.println("CodeLoadTest的构造块");
    }
    static {
        System.out.println("CodeLoadTest的静态代码块");
    }

    public CodeLoadTest() {
        System.out.println("CodeLoadTest的构造方法");
    }

    public static void main(String[] args) {
        System.out.println("-------------分割线1-------------");
        new AAA();
        System.out.println("-------------分割线2-------------");
        new AAA();
        System.out.println("-------------分割线3-------------");
        new CodeLoadTest();
    }
}
