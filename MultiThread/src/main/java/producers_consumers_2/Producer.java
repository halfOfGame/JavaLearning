package producers_consumers_2;

/**
 * @author halfOfGame
 * @create 2020-05-06,16:05
 */
public class Producer implements Runnable {
    private Box box;

    public Producer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            box.put(i);
        }
    }
}
