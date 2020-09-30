package learnthread;

public class Test {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++)
            System.out.println("main方法(1)------" + i);
        TestThread testThread = new TestThread();
        testThread.start();
        for (int i = 1; i <= 100; i++)
            System.out.println("main方法(2)------" + i);
    }
}
