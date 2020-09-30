package buytrainticket;

public class TicketOffice_2 implements Runnable {
    private static int remainingTicketNumber = 10;

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            synchronized (this) {
                if (remainingTicketNumber > 0) {
                    System.out.println("我在" + Thread.currentThread().getName() + "买到了票，剩余票数：" + (--remainingTicketNumber));
                }
            }
        }
    }
}
