package AlgorithmsFourthEdition.part1.practice;

/**
 * 创建一个N*N的数组，当i和j互质时A[i][j]为true，否则为false
 */

public class Practice1_1_30 {
    public static boolean[][] getArray(int N) {
        boolean[][] array = new boolean[N][N];
        for (int i = 0; i < N; i++){
            for (int j = 0; j <= i; j++) {
                if (relativelyPrime(i, j)) {
                    array[i][j] = true;
                    array[j][i] = true;
                } else {
                    array[i][j] = false;
                    array[j][i] = false;
                }
            }
        }
        return array;
    }

    public static boolean relativelyPrime(int p, int q) {
        //数组的行列都是从0开始，所以得进行+1操作
        p++;
        q++;
        if (p > q) {
            int temp = q;
            q = p;
            p = temp;
        }
        for (int i = 2; i <= p; i++) {
            if (p % i == 0 && q % i == 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int size = 10;
        boolean[][] array = getArray(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
}
