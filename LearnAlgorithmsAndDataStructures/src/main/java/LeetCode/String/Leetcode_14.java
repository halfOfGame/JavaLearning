package LeetCode.String;

/**
 *Descript:
 *  Find the longest common profix string amongst an array of strings.
 */

public class Leetcode_14 {
    public static String longestCommonPrefix(String[] strs) {
        //暴力法的改进版，省去了计算最短字符串的操作
        if (strs.length == 0) return "";
        char ch;
        for (int i = 0; i < strs[0].length(); i++) {
            ch = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || ch != strs[j].charAt(i))
                    return strs[0].substring(0, i);
            }
        }
        return strs[0];
        /*
        利用 F(s1, s2, s3) = F(F(s1, s2), s3)来实现
        if (strs.length == 0) return "";
        String longestProfix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            //若匹配到了，则函数的返回值为0，否则为-1
            while (strs[i].indexOf(longestProfix) != 0) {
                longestProfix = longestProfix.substring(0, longestProfix.length() - 1);
                if (longestProfix.isEmpty()) return "";
            }
        }
        return longestProfix;
         */
        /*
        自己想到的方法，暴力法
        if (strs.length == 0) return "";
        int matchedIndex = 0, len = strs.length, minLength = Integer.MAX_VALUE, i, j;
        for (i = 0; i < len; i++) {
            if (strs[i].length() < minLength) {
                minLength = strs[i].length();
            }
        }
        for (i = 0; i < minLength; i++) {
            for (j = 0; j < strs.length - 1; j++) {
                if (strs[j].charAt(i) != strs[j + 1].charAt(i)) {
                    return strs[0].substring(0, matchedIndex);
                }
            }
            matchedIndex++;
        }
        return strs[0].substring(0, matchedIndex);
        */
    }

    public static void main(String[] args) {
        String[] strs;
        strs = new String[]{"flower", "flow", "flight"};
        System.out.println(longestCommonPrefix(strs));
        strs = new String[]{"dog", "racecar", "car"};
        System.out.println(longestCommonPrefix(strs));
    }
}
