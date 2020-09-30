package LeetCode.Stack;

import java.util.HashMap;
import java.util.Stack;

/**
 * Describtion:
 *  determine if the parentheses are valid.
 */

public class LeetCode_20 {
    //用栈来实现
    public static boolean isValid(String s) {
        Stack<Character> characters = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                characters.push(ch);
            }
            if (characters.isEmpty()) return false;
            if (ch == ')') {
                if (characters.pop() != '(') return false;
            } else if (ch == ']') {
                if (characters.pop() != '[') return false;
            } else if (ch == '}'){
                if (characters.pop() != '{') return false;
            }
        }
        if (characters.isEmpty()) return true;
        else return false;
    }

    public static void main(String[] args) {
        System.out.println(isValid("()"));
        System.out.println(isValid("()[]{}"));
        System.out.println(isValid("(]"));
        System.out.println(isValid("([)]"));
        System.out.println(isValid("{[]}"));
        System.out.println(isValid("["));
        System.out.println(isValid("]"));
    }
}
