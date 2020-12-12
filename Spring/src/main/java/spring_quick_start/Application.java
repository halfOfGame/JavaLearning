package spring_quick_start;

public class Application {

    public static void main(String[] args) {

        System.out.println("Application");
        //创建打印机对象
        MessagePrinter printer = new MessagePrinter();
        //创建消息服务对象
        MessageService service = new MessageService();
        //设置打印机对象的service属性
        printer.setService(service);
        printer.printMessage();
    }
}
