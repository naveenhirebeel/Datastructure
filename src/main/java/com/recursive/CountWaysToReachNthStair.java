package com.recursive;

public class CountWaysToReachNthStair {

    public static void main(String[] args) {
        System.out.println(countWays(4));
        System.out.println(countWays(4, 2));
    }


    private static int fib(int n) {
        int[] res = new int[n+1];
        res[0] = 0;
        res[1] = 1;
        for(int i = 2; i<n+1; i++) {
            res[i] = res[i-1] + res[i-2];
        }
        return res[n];
    }
    private static int fib(int n, int steps) {
        int[] res = new int[n+1];
        res[0] = 0;
        res[1] = 1;
        for(int i = 2; i<n+1; i++) {
            res[i] = 0;
            for(int j = 1; j <=steps && j <= i; j++) {
                res[i]+=res[i - j];
            }
        }
        return res[n];
    }

    private static int countWays(int stairs) {
        return fib(stairs + 1);
    }
    private static int countWays(int stairs, int steps) {
        return fib(stairs +1, steps);
    }




}
