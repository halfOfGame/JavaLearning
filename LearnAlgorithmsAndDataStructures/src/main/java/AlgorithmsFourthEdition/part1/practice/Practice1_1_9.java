package AlgorithmsFourthEdition.part1.practice;

/**
 * 将一个整数转化为二进制数字符串
 * 注意点：利用短除法求余数后需要反向取数，此时令 s = (i % 2) + s 可完成操作
 */
public class Practice1_1_9 {
    public static String intToBinaryString(int number) {
        String s = "";
        for (int i = number; i > 0; i /= 2) {
            s += (i % 2);
        }
        return s;
    }

    public static void main(String[] args) {


    }
}
