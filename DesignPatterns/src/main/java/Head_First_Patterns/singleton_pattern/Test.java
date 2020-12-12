package Head_First_Patterns.singleton_pattern;

public class Test {
    public static void main(String[] args) {
        Singleton s1 = DoubleInspectSingleton.getInstance();
        Singleton s2 = DoubleInspectSingleton.getInstance();
        System.out.println(s1);
        System.out.println(s2);
    }
}
