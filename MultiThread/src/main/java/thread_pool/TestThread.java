package thread_pool;

public class TestThread implements Runnable {
    @Override
    public void run() {
        System.out.println("当前执行的任务为" + Thread.currentThread().getName());
    }
}
