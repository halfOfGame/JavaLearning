package ClassLoader;

/**
 * @author halfOfGame
 * @create 2020-03-18,19:27
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoaderTest classLoaderTest = new ClassLoaderTest();
        System.out.println(classLoaderTest.getClass().getClassLoader().getParent().getParent());
        System.out.println(classLoaderTest.getClass().getClassLoader().getParent());
        System.out.println(classLoaderTest.getClass().getClassLoader());

        System.out.println("----------------");

        Object o = new Object();
        System.out.println(o.getClass().getClassLoader());
    }
}
