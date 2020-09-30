package GrabageCollection;

import java.util.Scanner;

/**
 * @author halfOfGame
 * @create 2020-03-20,15:44
 */
public class GCTest_1 {
    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7, allocation8;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation5 = new byte[4 * _1MB];
        Scanner scanner = new Scanner(System.in);
        scanner.nextInt();
//        allocation6 = new byte[4 * _1MB];
//        allocation7 = new byte[4 * _1MB];
//        allocation8 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testAllocation();
    }
}
