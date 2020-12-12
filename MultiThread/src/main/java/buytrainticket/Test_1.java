package buytrainticket;

public class Test_1 {
    public static void main(String[] args) {
        TicketOffice_1 ticketOffice1 = new TicketOffice_1("窗口1");
        ticketOffice1.start();
        TicketOffice_1 ticketOffice2 = new TicketOffice_1("窗口2");
        ticketOffice2.start();
        TicketOffice_1 ticketOffice3 = new TicketOffice_1("窗口3");
        ticketOffice3.start();
    }
}
