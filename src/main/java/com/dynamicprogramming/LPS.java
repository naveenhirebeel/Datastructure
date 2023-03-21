package com.dynamicprogramming;

public class LPS {

    public static void main(String[] args) {
        String str = "abba";
        System.out.println(lpsRec(str, 0, str.length()-1));
        System.out.println(lps(str));
    }

    private static int lpsRec(String str, int m, int n) {
        if(m > n)
            return 0;
        if(m == n)
            return 0;
        if(str.charAt(m) == str.charAt(n))
            return 2 +lpsRec(str, m+1, n-1);
        else
            return Math.max(lpsRec(str, m+1, n), lpsRec(str, m, n-1));
    }

    private static int lps(String str) {
        int len = str.length();
        int[][] arr = new int[len][len];
        for(int c = 2; c <= len; c++) {
            for (int i = 0; i < len-c+1; i++) {
                int j = i+c-1;
                if(i == j)
                    arr[i][j] = 1;
                if(str.charAt(i) == str.charAt(j))
                    arr[i][j] = arr[i+1][j-1] + 2;
                else
                    arr[i][j] = Math.max(arr[i][j-1], arr[i+1][j]);
            }
        }
        return arr[0][str.length()-1];
    }
}
