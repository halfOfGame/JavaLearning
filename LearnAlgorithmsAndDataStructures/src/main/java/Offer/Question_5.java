package Offer;

/**
 * @author halfOfGame
 * @create 2020-03-24,17:15
 */

/**
 * 请实现一个函数，把字符串 s 中的每个空格替换成"%20"。
 */
public class Question_5 {
    public static String replaceSpace(String s) {
        if (s == null || s.length() == 0)
            return s;
        char[] chars = s.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != ' ') {
                stringBuilder.append(chars[i]);
            } else {
                stringBuilder.append("%20");
            }
        }
        return stringBuilder.toString();
    }
}
