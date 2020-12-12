package callableimpl;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


//有返回值
public class RandomNumber implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("----线程开始执行----");
        Thread.sleep(1000);
        return new Random().nextInt(100);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        RandomNumber randomNumber = new RandomNumber();
        FutureTask futureTask = new FutureTask(randomNumber);
        Thread thread = new Thread(futureTask);
        thread.start();

        System.out.println("当前线程是否执行完：" + futureTask.isDone());
        Integer number = (Integer)(futureTask.get());
        System.out.println(number);
        System.out.println("当前线程是否执行完：" + futureTask.isDone());
    }
}
