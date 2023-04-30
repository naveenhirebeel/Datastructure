package com.datastructure.dynamicprogramming;

import java.util.Arrays;

public class LISWithHighestSum {

    public static void main(String[] args) {
        int[] arr = {10, 22, 9, 33, 21, 50, 41, 60, 80};

        int[] lis = new int[arr.length];
        for(int i = 0; i < arr.length; i++)
            lis[i] = 1;

        int[] lisSum = new int[arr.length];
        for(int i = 0; i < arr.length; i++)
            lisSum[i] = arr[i];

        for(int i=1; i<arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i] && lis[i] < lis[j] + 1) {
                    lis[i] = lis[j] + 1;
                }

                if (arr[j] < arr[i] && lisSum[i] < lisSum[j] + arr[i]) {
                    lisSum[i] = lisSum[j] + arr[i];
                }
            }
        }

        Arrays.stream(arr).forEach(a -> System.out.print(a+" "));
        System.out.println();
        Arrays.stream(lis).forEach(a -> System.out.print(a+" "));
        System.out.println();
        Arrays.stream(lisSum).forEach(a -> System.out.print(a+" "));
    }
}
