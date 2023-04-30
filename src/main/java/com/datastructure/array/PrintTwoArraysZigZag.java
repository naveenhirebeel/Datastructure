package com.datastructure.array;

public class PrintTwoArraysZigZag {

    public static void main(String[] args) {
        int[] arr1 = {0,1,2,3,4};
        int[] arr2 = {5,6,7,8,9};
        int l = 0;
        int r  = arr1.length-1;
        boolean flag = true;
        while(l<=r) {
            if(flag) {
                System.out.println(arr1[l]+","+arr2[r]);
                System.out.println(arr2[l]+","+arr1[r]);
            } else {
                System.out.println(arr1[l]+","+arr2[r]);
                System.out.println(arr2[l]+","+arr1[r]);
            }
            flag = !flag;
            l++;
            r--;
        }
    }

}
