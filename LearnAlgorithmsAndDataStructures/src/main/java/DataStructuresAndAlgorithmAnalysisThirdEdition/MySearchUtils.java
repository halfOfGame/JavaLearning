package DataStructuresAndAlgorithmAnalysisThirdEdition;

/**
 * @author halfOfGame
 * @create 2020-05-04,18:28
 */
public class MySearchUtils {

    public static int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int i = 0, j = nums.length - 1, mid;
        while (true) {
            mid = (i + j) / 2;
            if (target < nums[mid]) {
                j = mid - 1;
            } else if (target > nums[mid]) {
                i = mid + 1;
            } else {
                return mid;
            }
            if (i > j) {
                return -1;
            }
        }
    }
}
