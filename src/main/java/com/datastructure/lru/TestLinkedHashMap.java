package com.datastructure.lru;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestLinkedHashMap {

    public static void main(String[] args) {
        Map map = new LinkedHashMap(4, 0.75f, true);
        map.put(1,1);
        map.put(2,2);
        map.put(3,3);
        map.put(4,4);
        map.put(5,5);
        System.out.println(map);
        map.get(3);
        System.out.println(map);
    }
}

interface MyTest {
    default void hello() {
        System.out.println("Welcome");
    }
}
