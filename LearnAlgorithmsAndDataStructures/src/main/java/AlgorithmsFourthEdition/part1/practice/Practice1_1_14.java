package AlgorithmsFourthEdition.part1.practice;

/**
 * 接受一个整型实数，返回不大于log2(N)的最大实数
 */

public class Practice1_1_14 {
    public static int getNumber(int num) {
        int s = 0;
        while (num > 1) {
            s++;
            num /= 2;
        }
        return s;
    }

    public static void main(String[] args) {
        System.out.println(getNumber(1));
    }
}
