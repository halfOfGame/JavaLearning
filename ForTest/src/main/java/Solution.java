import javax.xml.stream.events.Characters;
import java.util.*;

public class Solution {
    public static ArrayList<Integer> spiralOrder(int[][] matrix) {
        ArrayList<Integer> result = new ArrayList();
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return result;
        }
        int rowCount = matrix.length, columnCount = matrix[0].length;
        int left = 0, right = columnCount - 1, top = 0, bottom = rowCount - 1;
        while (true) {
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++;
            if (top > bottom) {
                break;
            }
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            if (left > right) {
                break;
            }
            for (int i = right; i >= left; i--) {
                result.add(matrix[bottom][i]);
            }
            bottom--;
            if (top > bottom) {
                break;
            }
            for (int i = bottom; i >= top; i--) {
                result.add(matrix[i][left]);
            }
            left++;
            if (left > right) {
                break;
            }
        }
        return result;
    }

    public static int solve(int[] a) {
        int leftIndex = 0, rightIndex = a.length - 1;
        while (leftIndex < rightIndex) {
            int middleIndex = (leftIndex + rightIndex) / 2;
            if (a[middleIndex] > middleIndex) {
                rightIndex = middleIndex;
            } else {
                leftIndex = middleIndex + 1;
            }
        }
        return leftIndex;
    }

    public static void reOrderArray(int[] array) {
        int leftIndex = 0, rightIndex = array.length - 1;
        while (leftIndex < rightIndex) {
            while (leftIndex < rightIndex && array[leftIndex] % 2 == 1) {
                leftIndex++;
            }
            while (leftIndex < rightIndex && array[rightIndex] % 2 == 0) {
                rightIndex--;
            }
            int tempValue = array[leftIndex];
            array[leftIndex] = array[rightIndex];
            array[rightIndex] = tempValue;
        }
    }

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack();
        stack.push(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            char tempChar = s.charAt(i);
            if ((tempChar == ')' && stack.peek() == '(') || (tempChar == ']' && stack.peek() == '[') || (tempChar == '}' && stack.peek() == '{')) {
                stack.pop();
            } else {
                stack.push(tempChar);
            }
        }
        return stack.isEmpty();
    }

    public static int[] getMinStack(int[][] op) {
        Stack<Integer> stackOne = new Stack();
        Stack<Integer> stackTwo = new Stack();
        List<Integer> result = new ArrayList();
        for (int[] nums : op) {
            if (nums[0] == 1) {
                stackOne.push(nums[1]);
                if (stackTwo.isEmpty() || (stackTwo.peek() >= nums[1])) {
                    stackTwo.push(nums[1]);
                }
            } else if (nums[0] == 2) {
                if (!stackTwo.isEmpty() && stackOne.peek().intValue() == stackTwo.peek().intValue()) {
                    stackTwo.pop();
                }
                stackOne.pop();
            } else {
                result.add(stackTwo.peek());
            }
        }
        return result.stream().mapToInt(Integer::valueOf).toArray();
    }

    public static void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        int tempValue = 0, tempValueTwo = 1;
        for (int num : array) {
            tempValue = tempValue ^ num;
        }
        while ((tempValue & 1) == 0) {
            tempValue = tempValue >> 1;
            tempValueTwo = tempValueTwo << 1;
        }
        for (int num : array) {
            if ((num & tempValueTwo) == 0) {
                num1[0] = num1[0] ^ num;
            } else {
                num2[0] = num2[0] ^ num;
            }
        }
    }

    public static void main(String[] args) {
        char c = 'a';
        char b = (char)('a' - 32);
        System.out.println(b);
    }
}
