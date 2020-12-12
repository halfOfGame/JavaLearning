package JMM;

/**
 * @author halfOfGame
 * @create 2020-03-19,18:29
 */


class MyNumber1 {
    int number = 10;

    public void add() {
        number++;
    }
}

public class JMMTest1 {
    public static void main(String[] args) {
        MyNumber1 myNumber1 = new MyNumber1();

        new Thread(() -> {
            System.out.println("-----Enter " + Thread.currentThread().getName() +"-----");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myNumber1.add();
            System.out.println(Thread.currentThread().getName() + " update number! number = " + myNumber1.number);
        }, "Child thread").start();

        //The main thread can't receive the message that the number was modified. So the program will stop here.
        while (myNumber1.number == 10) {

        }

        System.out.println(Thread.currentThread().getName() + "Mission accomplished!");
    }
}
