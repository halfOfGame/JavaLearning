import java.util.HashMap;

/**
 * @author halfOfGame
 * @create 2020-04-30,17:47
 */
class Solution_1 {

    public class UnionFind {
        int max;
        HashMap<Integer, Integer> fatherMap;
        HashMap<Integer, Integer> sizeMap;

        public UnionFind(int[] nums) {
            max = 1;//处理nums中只有一个元素的情况下，默认为1
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();

            for (int val : nums) {
                fatherMap.put(val, val);
                sizeMap.put(val, 1);
            }
        }

        public int findFather(int val) {
            int father = fatherMap.get(val);
            if (father != val) {
                father = findFather(father);
            }
            fatherMap.put(val, father);
            return father;
        }

        public void union(int a, int b) {
            int aFather = findFather(a);
            int bFather = findFather(b);
            if (aFather != bFather) {
                int aSize = sizeMap.get(aFather);
                int bSize = sizeMap.get(bFather);
                
                fatherMap.put(aFather, bFather);
                sizeMap.put(bFather, aSize + bSize);

                max = Math.max(max, aSize + bSize);
            }
        }
    }

    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        UnionFind uf = new UnionFind(nums);
        for (int i = 0; i < nums.length; i++) {
            if (uf.fatherMap.containsKey(nums[i] - 1)) {
                uf.union(nums[i] - 1, nums[i]);
            }
        }
        return uf.max;
    }

    public static void main(String[] args) {
        int[] nums = {100, 4, 200, 1, 3, 2};
        int maxContinuousLength = new Solution_1().longestConsecutive(nums);
        System.out.println(maxContinuousLength);
    }
}
