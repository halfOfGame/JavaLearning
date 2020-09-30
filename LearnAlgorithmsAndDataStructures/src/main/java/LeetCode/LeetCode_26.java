package LeetCode;

/**
 * @author halfOfGame
 * @create 2020-03-21,23:46
 */
public class LeetCode_26 {
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int num = nums[0];
        int index = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != num) {
                num = nums[i];
                int temp = nums[index];
                nums[index] = nums[i];
                nums[i] = temp;
                index++;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1};
        int num = removeDuplicates(nums);
        for (int i = 0; i < num; i++) {
            System.out.println(nums[i]);
        }
    }
}
