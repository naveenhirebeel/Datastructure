package com.dynamicprogramming;

import java.util.Arrays;
import java.util.List;

public class SmallestInteger {

    public static void main(String[] args) {
        List<Integer> arr = Arrays.asList(1,2, 3,5,7,9);
        System.out.println(smallestInteger(arr));
    }

    private static int smallestInteger(List<Integer> list) {
        int res = 1;

        for(int i : list) {
            if(i>res) {
                return res;
            } else {
                res += i;
            }
        }
        return res;
    }
}
