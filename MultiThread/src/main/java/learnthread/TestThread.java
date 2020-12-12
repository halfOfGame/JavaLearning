package learnthread;

public class TestThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 100; i++)
            System.out.println("TestThread------" + i);
    }
}
