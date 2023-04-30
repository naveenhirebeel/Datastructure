package com.datastructure.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortedListToSquareOfList {

    public static void main(String[] args) {
//        List<Integer> input = Arrays.asList(1,2,3);
//        List<Integer> input = Arrays.asList(1,2,3,4);
        List<Integer> input = Arrays.asList(-9,-7,-5,-2,-1,0,1,3,6,8,10);
        System.out.println(squareOfListSorted(input));
    }

    private static List<Integer> squareOfListSorted(List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        int l = 0;
        int r = list.toArray().length - 1;
        while(l <= r) {
            int lv = list.get(l)*list.get(l);
            int rv = list.get(r)*list.get(r);
            if(lv < rv) {
                result.add(0, rv);
                r--;
            } else if (lv >= rv) {
                result.add(0, lv);
                l++;
            }
        }
        return result;
    }
}
