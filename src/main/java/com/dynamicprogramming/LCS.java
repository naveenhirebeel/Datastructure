package com.dynamicprogramming;

public class LCS {

    public static void main(String[] args) {
        String s1 = "AGGTAB";
        String s2 = "GXTXAYB";

        char[] X = s1.toCharArray();
        char[] Y = s2.toCharArray();

        int m = s1.length();
        int n = s2.length();
        System.out.println(lcs(X, Y, m, n));

    }

    private static int lcs(char[] X, char[] Y, int m, int n) {
        if(m == 0 || n == 0) {
            return 0;
        }
        if (X[m-1] == Y[n-1])
            return lcs(X,Y,m-1,n-1) + 1;
        else
            return Math.max(lcs(X,Y,m,n-1), lcs(X,Y,m-1,n));
    }

    static int lcsFinal(char[] X, char[] Y, int m, int n) {
        int[][] arr = new int[m+1][n+1];
        for(int i = 0; i<=m; i++) {
            for(int j = 0; j<=n; j++) {
                if(i == 0 || j ==0) {
                    arr[i][j] = 0;
                } else if(X[i-1] == Y[j-1]) {
                    arr[i][j] = arr[i-1][j-1] + 1;
                } else {
                    arr[i][j] = Math.max(arr[i-1][j], arr[i][j-1]);
                }
            }
        }

        System.out.println("LCS : "+arr[m][n]);
        return arr[m][n];
    }

    static void print(int[][] arr, int m, int n) {
        for (int i = 0; i<=m; i++) {
            for(int j = 0; j<=n; j++) {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }
}
