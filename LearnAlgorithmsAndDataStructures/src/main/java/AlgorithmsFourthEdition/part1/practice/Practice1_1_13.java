package AlgorithmsFourthEdition.part1.practice;

/**
 * 打印出一个M*N的矩阵的转置
 */
public class Practice1_1_13 {
    public static void matrixTranspose(int[][] nums, int M, int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++)
                System.out.print(nums[j][i] + " ");
            System.out.println();
        }
    }
}
