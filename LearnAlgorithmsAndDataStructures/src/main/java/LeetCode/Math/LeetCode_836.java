package LeetCode.Math;

/**
 * @author halfOfGame
 * @create 2020-03-18,12:40
 */

/**Determine whether the two rectangles overlap
 * (x1, y1, x2, y2) botton-left and top-right
 */
public class LeetCode_836 {
    public static boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        return !(rec1[0] >= rec2[2] || rec1[1] >= rec2[3] || rec1[2] <= rec2[0] || rec1[3] <= rec2[1]);
    }

    public static void main(String[] args) {
        int[] rec1 = new int[]{0, 0, 2, 2};
        int[] rec2 = new int[]{1, 1, 3, 3};
        System.out.println(isRectangleOverlap(rec1, rec2));
        rec1 = new int[]{0, 0, 1, 1};
        rec2 = new int[]{1, 0, 2, 1};
        System.out.println(isRectangleOverlap(rec1, rec2));
    }
}
