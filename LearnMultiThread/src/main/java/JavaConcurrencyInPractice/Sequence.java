package JavaConcurrencyInPractice;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author halfOfGame
 * @create 2020-05-06,19:08
 */
public class Sequence {


    /**
     * 这个类是线程不安全的，因为 value++不是原子操作
     */
    class UnsafeSequence {
        private int value;

        public int getNext() {
            return value++;
        }
    }

    /**
     * 这个类是线程安全的，因为加了同步操作
     */
    class SafeSequance {
        private int value;

        public synchronized int getNext() {
            return value++;
        }
    }


    /**
     * 这样可以完成任务，且保证线程安全
     */
    class CountingFactorizer {
        private final AtomicLong count = new AtomicLong(0);

        public long getCount() {
            return count.get();
        }

        public void service() {
            count.incrementAndGet();
        }
    }


    

}
