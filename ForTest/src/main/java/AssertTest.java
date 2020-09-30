import java.util.ArrayList;
import java.util.Arrays;

public class AssertTest {

    public static ArrayList<ArrayList<Integer>> subsets(int[] s) {
        ArrayList<ArrayList<Integer>> list = new ArrayList();
        list.add(new ArrayList());
        Arrays.sort(s);
        for (int i = 0; i < s.length; i++) {
            int size = list.size();
            for (int j = 0; j < size; j++) {
                ArrayList tempList = new ArrayList(list.get(j));
                tempList.add(s[i]);
                list.add(tempList);
            }
        }
        for (int i = 0; i < list.size() - 1; i++) {
            for(int j = 0; j < list.size() - i - 1; j++){
                if ((list.get(j).size() > list.get(j + 1).size()) || needSort(list.get(j), list.get(j + 1))){
                    ArrayList tempList = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tempList);
                }
            }
        }
        return list;
    }

    public static boolean needSort(ArrayList<Integer> nums1, ArrayList<Integer> nums2){
        if (nums1.size() != nums2.size())
            return false;
        for (int i = 0; i < nums1.size(); i++) {
            if (nums1.get(i) > nums2.get(i))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int[] s = new int[]{9, 0, 3, 5, 7};
        ArrayList<ArrayList<Integer>> list = subsets(s);
        for (int i = 0; i < list.size(); i++) {
            System.out.print("[");
            for (int j = 0; j < list.get(i).size(); j++){
                System.out.print(list.get(i).get(j) + ",");
            }
            System.out.print("], ");
        }
    }
}
