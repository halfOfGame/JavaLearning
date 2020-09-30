package DataStructuresAndAlgorithmAnalysisThirdEdition;

import java.util.HashMap;

/**
 * @author halfOfGame
 * @create 2020-05-02,16:07
 */

/**
 * 数据结构
 * max：记录节点集合的最大值
 * fatherMap：记录每个节点的父亲
 * sizeMap：记录每个节点所在集合的大小（最后用到的是那个最长连续节点串的最后一个节点）
 *
 * 步骤
 * 1.  初始化UnionFind，将所有节点指向自己，所有节点的size为1。
 * 2.  遍历节点，查看当前节点的上一节点是否在nums中，如果在则执行union方法。
 * 3.  a为上一节点，b为当前节点，查找两个节点的父亲节点(该操作会整理结构)aF和bF,
 * 	aF和bF对应的集合大小为aFS和bFS，将bF作为aF的父亲，bFS = aFS + bFS，
 * 	重置max遍历。
 */


public class UnionFind{
    int max = 1;
    HashMap<Integer,Integer> fatherMap;
    HashMap<Integer,Integer> sizeMap;

    public UnionFind(int[] nums){
        fatherMap = new HashMap<>();
        sizeMap = new HashMap<>();

        for(int val: nums){
            fatherMap.put(val,val);
            sizeMap.put(val,1);
        }
    }

    //找到父节点并优化结构,使所有节点都指向根节点
    public int findFather(int val){
        int father = fatherMap.get(val);
        if(father != val){
            father = findFather(father);
        }
        fatherMap.put(val,father);
        return father;
    }

    //合并两个节点，b节点作为a节点的父亲节点
    public void union(int a,int b){
        int aFather = findFather(a);
        int bFather = findFather(b);
        if(aFather != bFather){
            int  aFatherSize = sizeMap.get(aFather);
            int  bFatherSize = sizeMap.get(bFather);

            fatherMap.put(aFather, bFather);
            sizeMap.put(bFather,aFatherSize + bFatherSize);

            max = Math.max(max,aFatherSize + bFatherSize);
        }
    }
}
