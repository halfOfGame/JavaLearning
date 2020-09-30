package JavaInterview;

/**
 * @author halfOfGame
 * @create 2020-04-02,14:31
 */
public class SynchronizedDemo {
    public void method() {
        synchronized (this) {
            System.out.println("synchronized 代码块");
        }
    }
}
