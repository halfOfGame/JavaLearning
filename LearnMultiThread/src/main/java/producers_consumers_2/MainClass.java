package producers_consumers_2;

/**
 * @author halfOfGame
 * @create 2020-05-06,16:05
 */
public class MainClass {
    public static void main(String[] args) {
        Box box = new Box();
        Producer producer = new Producer(box);
        Consumer consumer = new Consumer(box);

        Thread thread1 = new Thread(producer,"生产者");
        Thread thread2 = new Thread(consumer,"消费者");

        thread1.start();
        thread2.start();
    }
}
