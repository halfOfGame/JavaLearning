package JavaConcurrencyInPractice.unit3;

/**
 * @author halfOfGame
 * @create 2020-05-07,9:20
 */

/**
 * P27~28  程序清单3-1
 *
 * read可能一直为false
 */
public class Novisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        number = 42;
        ready = true;
    }

}
