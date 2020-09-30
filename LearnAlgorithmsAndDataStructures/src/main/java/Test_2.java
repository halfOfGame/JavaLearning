import DataStructuresAndAlgorithmAnalysisThirdEdition.MyPriorityQueue;
import DataStructuresAndAlgorithmAnalysisThirdEdition.MySearchUtils;
import DataStructuresAndAlgorithmAnalysisThirdEdition.MySortUtils;
import DataStructuresAndAlgorithmAnalysisThirdEdition.MyTree;

/**
 * @author halfOfGame
 * @create 2020-05-04,8:26
 */
public class Test_2 {
    public static void main(String[] args) {
        Integer[] nums = {1, 2, 3, 4, 5, 6, 7};

        MyTree tree = new MyTree(nums);
        tree.postTraverseTree_Recursion(tree.head);
        System.out.println();
        System.out.print("---------------------");
        System.out.println();
        tree.postTraverseTree_Iteration(tree.head);
    }
}
