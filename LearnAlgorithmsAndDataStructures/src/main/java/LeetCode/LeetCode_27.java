package LeetCode;

/**
 * @author halfOfGame
 * @create 2020-03-22,8:30
 */
public class LeetCode_27 {
    public static int removeElement(int[] nums, int val) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[i] != nums[j]) {
                        int temp = nums[i];
                        nums[i] = nums[j];
                        nums[j] = temp;
                        break;
                    } else if (j == nums.length - 1) {
                        return count;
                    }
                }
            } else {
                count++;
            }
        }
        return ++count;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 2, 3, 0, 4, 2};
        int num = removeElement(nums, 2);
        for (int i = 0; i < num; i++) {
            System.out.println(nums[i]);
        }
    }
}
