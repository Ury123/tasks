package com.ury;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();

//        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
//        myHashMap.put("a", 1);
//        myHashMap.put("b", 2);
//        myHashMap.remove("b");
//        System.out.println(myHashMap.get("b"));

        MyHashMap2<Integer, Integer> myHashMap2 = new MyHashMap2<>(64, 0.75f);
        for (int i = 0; i < 11; i++) {
            int k = i*64 + 1;
            myHashMap2.put(k, i);
        }

        System.out.println(myHashMap2.size());
        System.out.println(myHashMap2.getOrDefault(500, 100));
//       myHashMap2.remove(641);
        myHashMap2.remove(1);
        System.out.println(myHashMap2.size());
        System.out.println(myHashMap2.get(641));
    }
}