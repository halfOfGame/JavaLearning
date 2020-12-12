package producers_consumers;

public class Test {
    public static void main(String[] args) {
        Production production = new Production();
        ProducerThread producerThread = new ProducerThread(production);
        ConsumerThread consumerThread = new ConsumerThread(production);
        producerThread.start();
        consumerThread.start();
    }
}
