import DataStructuresAndAlgorithmAnalysisThirdEdition.MySortUtils;


public class Test {
    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static int findRepeatNumber(int[] nums) {
        int n = nums.length;
        //1~ n -1
        for (int num : nums)
            if (num < 0 || num >= n)
                return -1;
        //利用下标交换 保证下标 == 元素值
        for (int i = 0; i < n; i++) {
            while (nums[i] != i && nums[nums[i]] != nums[i])
                swap(nums, i, nums[i]);//交换nums[i]  nums[nums[i]]
            if (nums[i] != i)
                return nums[i];
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 1, 0, 2, 5, 3};
        System.out.println(findRepeatNumber(nums));
    }
}
