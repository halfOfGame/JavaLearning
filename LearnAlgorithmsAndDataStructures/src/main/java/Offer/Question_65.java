package Offer;

/**
 * @author halfOfGame
 * @create 2020-04-01,9:05
 */

/**
 * 写一个函数，求两个整数之和，要求在函数体内不得使用 “+”、“-”、“*”、“/” 四则运算符号。
 */
public class Question_65 {
    public static int add(int a, int b) {
        while (b != 0) {
            int sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return a;
    }

    public static void main(String[] args) {
        System.out.println(add(7, 5));
    }
}
