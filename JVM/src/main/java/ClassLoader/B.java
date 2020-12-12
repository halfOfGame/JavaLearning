package ClassLoader;

/**
 * @author halfOfGame
 * @create 2020-03-20,20:37
 */
public class B {
    A a = new A();

    public static void main(String[] args) {
        B b = new B();
        b.a.Hello();
        System.out.println("Hello world");
    }
}
