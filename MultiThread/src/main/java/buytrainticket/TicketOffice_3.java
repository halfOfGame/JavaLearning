package buytrainticket;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketOffice_3 implements Runnable {
    private static int remainingTicketNumber = 10;
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            lock.lock();
            try {
                if (remainingTicketNumber > 0) {
                    System.out.println("我在" + Thread.currentThread().getName() + "买到了票，剩余票数：" + (--remainingTicketNumber));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
