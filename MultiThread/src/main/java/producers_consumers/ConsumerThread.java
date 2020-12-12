package producers_consumers;

public class ConsumerThread extends Thread {
    private Production production;

    public ConsumerThread(Production product) {
        this.production = product;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            production.consume();
        }
    }
}
