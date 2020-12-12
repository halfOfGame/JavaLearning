package NewJavaMulti;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Three {

    //继承类
    static class ExtendsThread extends Thread {
        @Override
        public void run() {
            System.out.println("I'm ExtendsThread");
        }
    }

    //实现Runnable接口
    static class RunnableThread implements Runnable {

        @Override
        public void run() {
            System.out.println("I'm RunnableThread");
        }
    }


    //实现Callable接口,可以有返回值
    static class CallableThread implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "I'm CallableThread";
        }
    }


    public static void main(String[] args) {

        //第一种方式
        ExtendsThread extendsThread = new ExtendsThread();
        extendsThread.start();

        //第二种方式
        RunnableThread runnable = new RunnableThread();
        new Thread(runnable).start();
        new Thread(runnable).start();


        //第三种方式
        FutureTask<String> futureTask = new FutureTask<>(new CallableThread());
        new Thread(futureTask).start();
        try {
            String result = futureTask.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
