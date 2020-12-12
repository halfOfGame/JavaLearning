package JMM;

/**
 * @author halfOfGame
 * @create 2020-03-19,18:41
 */

class MyNumber2 {
    volatile int number = 10;

    public void add() {
        number = 11;
    }
}

public class JMMTest2 {
    public static void main(String[] args) {
        MyNumber2 myNumber2 = new MyNumber2();

        new Thread(() -> {
            System.out.println("-----Enter " + Thread.currentThread().getName() +"-----");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myNumber2.add();
            System.out.println(Thread.currentThread().getName() + " update number! number = " + myNumber2.number);
        }, "Child thread").start();

        //The main thread can't receive the message that the number was modified. So the program will stop here.
        while (myNumber2.number == 10) {

        }
        System.out.println(Thread.currentThread().getName() + "Mission accomplished!");
    }
}
