package buytrainticket;

public class TicketOffice_1 extends Thread {
    private static int ticketNumber = 10;

    public TicketOffice_1(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            //加锁
            synchronized (TicketOffice_1.class) {
                if (ticketNumber > 0)
                    System.out.println("我在" + this.getName() + "买到了票，" + "余票:" + (--ticketNumber));
            }

        }
    }
}
