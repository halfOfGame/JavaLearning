import java.util.Stack;

class Solution {
    public static boolean validateStackSequences(int[] pushed, int[] popped) {
        if (pushed == null || popped == null || pushed.length == 0 || popped.length == 0) {
            return true;
        }
        int index = 0, len = pushed.length;
        Stack<Integer> stack = new Stack<>();
        for (int num : popped) {
            stack.push(pushed[index++]);
            while (stack.peek() != num) {
                if (index >= len) {
                    return false;
                }
            }
            stack.pop();
        }
        return true;
    }


    public static void main(String[] args) {
        int[] pushed = {1,2,3,4,5};
        int[] poped = {4,5,3,2,1};
        System.out.println(validateStackSequences(pushed, poped));
    }
}
