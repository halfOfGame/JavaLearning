package Offer;

/**
 * @author halfOfGame
 * @create 2020-04-01,8:31
 */
public class Question_66 {
    public static int[] constructArr(int[] a) {
        if (a == null || a.length == 1)
            return a;
        int len = a.length, res = 1;
        int[] nums = new int[len];
        for (int i = 0; i < len - 1; i++) {
            res *= a[i];
            nums[i + 1] = res;
        }
        res = 1;
        nums[0] = 1;
        for (int i = len - 1; i > 0; i--) {
            res *= a[i];
            nums[i - 1] *= res;
        }
        return nums;
    }

    public static void main(String[] args) {
        int[] a = new int[]{1,2,3,4,5};
        for (int i : constructArr(a))
            System.out.print(i + " ");
    }
}
