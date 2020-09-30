package buytrainticket;

public class Test_2 {
    public static void main(String[] args) {
        TicketOffice_2 ticketOffice_2 = new TicketOffice_2();
        new Thread(ticketOffice_2, "窗口1").start();
        new Thread(ticketOffice_2, "窗口2").start();
        new Thread(ticketOffice_2, "窗口3").start();
    }
}
