package AlgorithmsFourthEdition.part1.study;

/**
 * 利用递归求最大公约数
 */

public class Algorithms1 {
    public static int gcd (int p, int q) {
        if (q == 0) return p;
        int temp = p % q;
        return gcd(q, temp);
    }
}
