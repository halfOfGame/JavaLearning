package DataStructuresAndAlgorithmAnalysisThirdEdition;

/**
 * @author halfOfGame
 * @create 2020-05-04,12:17
 */
public class MyPriorityQueue {
    //下标为0的位置不使用，
    private int[] array;
    private int size = 0;



    public MyPriorityQueue(int size) {
        array = new int[size + 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(int num) {
        array[++size] = num;
        swim(size);
    }

    private void exchange(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public int delMax() {
        int maxNum = array[1];
        exchange(array, 1, size);
        array[size--] = 0;
        sink(1);
        return maxNum;
    }

    /**
     * 如果节点比父亲节点大，则上浮
     * @param index
     */
    private void swim(int index) {
        while (index > 1 && array[index / 2] < array[index]) {
            exchange(array, index / 2, index);
            index /= 2;
        }
    }

    /**
     * 如果节点比子节点小，则下沉
     * @param index
     */
    private void sink(int index) {
        while (2 * index <= size) {
            int j = 2 * index;
            //选取子节点中那个较大的
            if (j < size && array[j] < array[j + 1]) {
                j++;
            }
            if (array[index] >= array[j]) {
                break;
            }
            exchange(array, index, j);
            index = j;
        }
    }
}
