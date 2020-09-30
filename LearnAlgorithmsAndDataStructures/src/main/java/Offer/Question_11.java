package Offer;

/**
 * @author halfOfGame
 * @create 2020-03-24,19:46
 */

import java.util.Arrays;

/**
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，
 * 输出旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。  
 *
 */

public class Question_11 {
    public static int minArray(int[] numbers) {
        return Math.min(numbers[0], assist(numbers, 0, numbers.length - 1));
    }

    public static int assist(int[] numbers, int start, int end) {
        if (start == end)
            return numbers[start];
        else if (numbers[start] <= numbers[start + 1])
            return assist(numbers, (start + end) / 2, numbers.length - 1);
        else
            return numbers[start];
    }
}
