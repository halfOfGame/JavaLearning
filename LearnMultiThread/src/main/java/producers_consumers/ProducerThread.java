package producers_consumers;

public class ProducerThread extends Thread {
    private Production production;

    public ProducerThread(Production product) {
        this.production = product;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                production.product("卫龙", "海带");
            } else {
                production.product("天润", "奶啤");
            }

        }
    }
}
