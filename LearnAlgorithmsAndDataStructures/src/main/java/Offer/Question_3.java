package Offer;

/**
 * @author halfOfGame
 * @create 2020-03-24,16:47
 */

import java.util.HashSet;
import java.util.Set;

/**
 * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，
 * 也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
 *
 */
public class Question_3 {
    public static int findRepeatNumber(int[] nums) {
        Set<Integer> numSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!numSet.add(nums[i])) {
                return nums[i];
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,1};
        System.out.println(findRepeatNumber(nums));
    }
}
