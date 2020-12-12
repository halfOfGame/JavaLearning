package Head_First_Patterns.singleton_pattern;

public class DoubleInspectSingleton {

    private static volatile Singleton instance;

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
