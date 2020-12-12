package JavaInterview;

/**
 * @author halfOfGame
 * @create 2020-04-02,15:47
 */

/**
 * 双重校验锁实现单例模式
 *
 * 为什么用双重if ? 因为第一次调用函数时才需要进行初始化，同步都比较耗时，
 * 所以当uniqueInstance不为空时则不需要同步操作。
 *
 * 为什么用volatile关键字，uniqueInstance = new Singleton()过程分为三个步骤。。。
 */
public class Singleton {
    private volatile static Singleton uniqueInstance;

    private Singleton(){

    }

    public static Singleton getUniqueInstance() {
        if (uniqueInstance == null){
            synchronized (Singleton.class){
                if (uniqueInstance == null) {
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }

}
