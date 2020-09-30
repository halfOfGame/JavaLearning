package MemoryParameterOptimization;

import java.util.Random;

/**
 * @author halfOfGame
 * @create 2020-03-18,21:32
 */
public class ModefyMemoryParameter {
    public static void main(String[] args) {
        //-Xms1024m -Xmx1024m -XX:+PrintGCDetails
//        long maxMemory = Runtime.getRuntime().maxMemory();
//        long totalMemory = Runtime.getRuntime().totalMemory();
//        System.out.println("-Xmx:MAX_MEMORY = " + maxMemory + "Byte");
//        System.out.println("-Xms:TOTAL_MEMORY = " + totalMemory + "Byte");
        String message = "Hello world";
        System.out.println("---------------------");
        while (true) {
            message += message + new Random().nextInt(888888898) + new Random().nextInt(999999999);
        }
    }
}
