package producers_consumers;

public class Production {
    private String brand;
    private String name;
    boolean needProduct = true;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public Production() {
        super();
    }

    public Production(String brand, String name) {
        this.brand = brand;
        this.name = name;
    }

    public synchronized void product(String brand, String name) {
        if (!needProduct) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.brand = brand;
        this.name = name;
        System.out.println("生产者生产了" + brand + name);
        needProduct = false;
        notify();
    }

    public synchronized void consume() {
        if (needProduct) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("消费者消费了" + brand + name);
        needProduct = true;
        notify();
    }
}
