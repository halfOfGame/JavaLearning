package thread_pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,
                2,
                3,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(3));

        threadPoolExecutor.execute(new TestThread());

        threadPoolExecutor.execute(new TestThread());

        threadPoolExecutor.execute(new TestThread());

        threadPoolExecutor.execute(new TestThread());

        threadPoolExecutor.execute(new TestThread());

//        threadPoolExecutor.execute(new TestThread());


        threadPoolExecutor.shutdown();
    }
}
