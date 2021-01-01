import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CTest {

    public void TestRemoveList() {

    }


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        System.out.println("移除前");
        for (Integer num : list) {
            System.out.print(num + " ");
        }

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            if ((iterator.next() & 1) != 0) {
                iterator.remove();
            }
        }

        System.out.println("\n移除后");
        for (Integer num : list) {
            System.out.print(num + " ");
        }
    }
}
