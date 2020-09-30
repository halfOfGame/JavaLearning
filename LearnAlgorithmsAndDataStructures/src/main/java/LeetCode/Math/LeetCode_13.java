package LeetCode.Math;

/**
 * Purpose:
 *  Roman to integer
 * example:
 *  III -> 3
 */

public class LeetCode_13 {
    public static int romanToInt(String s) {
        int i, num = 0, len = s.length();
        int[] nums = new int[len];
        char[] chars = s.toCharArray();
        for (i = 0; i < len; i++) {
            switch (chars[i]) {
                case 'I':
                    nums[i] = 1;
                    break;
                case 'V':
                    nums[i] = 5;
                    break;
                case 'X':
                    nums[i] = 10;
                    break;
                case 'L':
                    nums[i] = 50;
                    break;
                case 'C':
                    nums[i] = 100;
                    break;
                case 'D':
                    nums[i] = 500;
                    break;
                case 'M':
                    nums[i] = 1000;
                    break;
                default:
                    nums[i] = 0;
            }
        }
        for (i = 0; i < len - 1; i++) {
            if (nums[i] >= nums[i + 1]) {
                num += nums[i];
            } else {
                num += (nums[i + 1] - nums[i]);
                i++;
            }
        }
        if (i < len)
            num += nums[len - 1];
        return num;
    }

    public static void main(String[] args) {
        System.out.println(romanToInt("III"));
        System.out.println(romanToInt("IV"));
        System.out.println(romanToInt("IX"));
        System.out.println(romanToInt("LVIII"));
        System.out.println(romanToInt("MCMXCIV"));
    }
}
