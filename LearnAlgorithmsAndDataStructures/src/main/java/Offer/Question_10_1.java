package Offer;

/**
 * @author halfOfGame
 * @create 2020-03-24,18:07
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项。斐波那契数列的定义如下：
 *
 * F(0) = 0,   F(1) = 1
 * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
 * 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
 *
 * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
 *
 */

public class Question_10_1 {
    public static int fib(int n) {
        List<Long> arrayList = new ArrayList<>();
        arrayList.add((long) 0);
        arrayList.add((long) 1);
        for (int i = 2; i <= n; i++) {
            arrayList.add(arrayList.get(i - 2) + arrayList.get(i - 1));
        }
        return (int)(arrayList.get(n) % 1000000007);
    }

    public static void main(String[] args) {
        System.out.println(fib(2));
        System.out.println(fib(5));
    }
}
