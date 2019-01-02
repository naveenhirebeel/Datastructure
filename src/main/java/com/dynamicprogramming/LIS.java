package com.dynamicprogramming;

import java.util.Arrays;

public class LIS {

	public int getList(Integer[] arr) {
		
		int len = arr.length;
		Integer lis[] = new Integer[len];
		for(int i = 0; i < len; i++) {
			lis[i] = 1;
		}
		
		for(int i = 1; i< len; i++) {
			for(int j = 0; j<i; j++) {
				if(arr[i] > arr[j] && lis[i] < lis[j]+1) {
					lis[i] = lis[j] + 1;
				}
			}
		}
		
		System.out.println(Arrays.asList(arr));
		System.out.println(Arrays.asList(lis));
		
		int max = 1;
		for ( int i = 0; i < len; i++ )
            if ( max < lis[i] )
               max = lis[i];
		
		return max;
	}

	public static void main(String[] args) {
		Integer[] arr = {10, 22, 9, 33, 21, 50, 41, 60, 80};
		new LIS().getList(arr);
	}
}
