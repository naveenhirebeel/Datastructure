package com.datastructure.array;

import java.util.*;

public class printOccuranceOfNumber {

//    Input = {1, 2, 1, 3, 3, 1, 4, 5}
//    Output = {1, 1, 1, 3, 3, 2, 4, 5}
    public static void main(String[] args) {
        List<Integer> input = Arrays.asList(1, 2, 1, 3, 3, 1, 4, 5);
        int[] output = new int[input.size()];
        Map<Integer, Integer>[] mapArray = new Map[input.size()];

        for(int i : input) {
            output[i] = output[i] + 1;
        }

        for (int i = 0; i < input.size(); i++) {
            int count = output[i];
            if(count == 0)
                continue;

            Map<Integer, Integer> map = mapArray[count];
            if(null == map) {
                map = new HashMap<>();
                mapArray[count] = map;
            }
            map.put(i, count);
        }

        for(int j = mapArray.length-1; j > 0; j --) {
            Map<Integer, Integer> map1 =  mapArray[j];
            if(map1 != null) {
                for(int key : map1.keySet()) {
                    int val = map1.get(key);
                    while (val > 0) {
                        System.out.print(key + " ");
                        val--;
                    }
                }
            }
        }
    }
}