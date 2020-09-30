import java.util.Arrays;

/**
 * @author halfOfGame
 * @create 2020-04-14,12:29
 */
public class Test_1 {

    public static int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0) return new int[0];
        int left = 0, right = matrix[0].length - 1, top = 0, bottom = matrix.length - 1, x = 0;
        int[] res = new int[(right + 1) * (bottom + 1)];
        while (true) {
            for (int i = left; i <= right; i++) res[x++] = matrix[top][i]; // left to right.
            if (++top > bottom) break;
            for (int i = top; i <= bottom; i++) res[x++] = matrix[i][right]; // top to bottom.
            if (left > --right) break;
            for (int i = right; i >= left; i--) res[x++] = matrix[bottom][i]; // right to left.
            if (top > --bottom) break;
            for (int i = bottom; i >= top; i--) res[x++] = matrix[i][left]; // bottom to top.
            if (++left > right) break;
        }
        return res;
    }

    public static int getSum(int n){
        int sum = n;
        boolean b = (n > 0) && ((sum += getSum(n - 1)) > 0);
        return sum;
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        int[] result = spiralOrder(matrix);
        System.out.println(Arrays.toString(result));
    }
}
