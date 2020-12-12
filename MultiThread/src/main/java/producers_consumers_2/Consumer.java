package producers_consumers_2;

/**
 * @author halfOfGame
 * @create 2020-05-06,16:04
 */
public class Consumer implements Runnable {
    private Box box;

    public Consumer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        while (true) {
            box.get();
        }
    }
}
