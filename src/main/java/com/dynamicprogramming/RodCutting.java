package com.dynamicprogramming;

public class RodCutting {
    
    public static int getMaxRevenue(int[] prices, int n) {
        int[] value = new int[n+1];
        value[0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                value[i] = Math.max(value[i], prices[j] + value[i-j-1]);
            }
        }
        return value[n];
    }

    public static void main(String[] args) {
        int[] prices = {2, 5, 6, 9, 10, 17, 17, 20};
        int n = 3;
        int maxRevenue = getMaxRevenue(prices, n);
        System.out.println("Maximum revenue for rod of length " + n + " is " + maxRevenue);
    }
}
