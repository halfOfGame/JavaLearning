package MyFirstAnnotation;

/**
 * @author halfOfGame
 * @create 2020-03-21,9:03
 */

@MyDescription(desc = "Hello world", author = "halfOfGame")
public class Test {

    @MyDescription(desc = "Hello Annotaion", author = "halfOfGame")
    public void Hello() {
        System.out.println("Hello halfOfGame");
    }
}
