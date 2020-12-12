package buytrainticket;

public class Test_3 {
    public static void main(String[] args) {
        TicketOffice_3 ticketOffice_3 = new TicketOffice_3();
        new Thread(ticketOffice_3, "窗口1").start();
        new Thread(ticketOffice_3, "窗口2").start();
        new Thread(ticketOffice_3, "窗口3").start();
    }
}
