package com.slqx;

import java.util.*;

public class Test {

    @org.junit.Test
    public void TestHashMap() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);
    }

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            map.put(random.nextInt(100), random.nextInt(100));
        }
        List<Integer> list = new ArrayList<>(map.values());
        Set<Integer> keySet = map.keySet();
        Set<Map.Entry<Integer, Integer>> otherMap = map.entrySet();
        System.out.println(map);
        System.out.println(keySet);
        System.out.println(otherMap);
        System.out.println(list);
    }
}
