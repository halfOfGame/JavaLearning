package producers_consumers_2;

/**
 * @author halfOfGame
 * @create 2020-05-06,16:03
 */
public class Box {
    private int milk;
    private boolean state = false;

    public void put(int milk) {
        //如果有牛奶，那就等待
        System.out.println("put begin");
        synchronized (this) {
            if (state) {
                try {
                    System.out.println("put wait");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("put wait end");
            }
            this.milk = milk;
            System.out.println("送奶工将第" + this.milk + "瓶牛奶放入奶箱");

            this.state = true;
            notifyAll();
        }

        System.out.println("put end");
    }

    public void get() {
        System.out.println("get begin");
        synchronized (this) {
            if (!state) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("用户拿到第" + this.milk + "瓶牛奶");
            this.state = false;
            notifyAll();
        }
        System.out.println("get end");
    }
}

