package JavaConcurrencyInPractice.unit3;

/**
 * @author halfOfGame
 * @create 2020-05-07,9:29
 */

/**
 * P29  程序清单3-3
 *
 */
public class SynchronizedInteger {
    private int value;

    public synchronized int getValue() {
        return value;
    }

    public synchronized void setValue(int value) {
        this.value = value;
    }

    public void add() {
        value++;
    }


    private static class ForTest extends Thread {

        public static SynchronizedInteger integer = new SynchronizedInteger();

        @Override
        public void run() {
            integer.setValue(1);
            System.out.println(integer.getValue());
        }
    }

    public static void main(String[] args) {
        new ForTest().start();
        new ForTest().start();
    }

}
